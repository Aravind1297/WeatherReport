import React, { useEffect, useState } from 'react'
import './Weather.css'
//import search_icon from '../assets/search.png'

import clear_icon from '../assets/clear.png';
import humidity_icon from '../assets/humidity.png';
import wind_icon from '../assets/wind.png';
import location_icon from '../assets/location.png';
import search_icon from '../assets/Search.png';

// Add more icons as needed
import drizzle_icon from '../assets/drizzle.png'; // fallback for drizzle
import rain_icon from '../assets/rain.png'; // fallback for rain
import snow_icon from '../assets/snow.png'; // fallback for snow
import clouds_icon from '../assets/cloud.png'; // fallback for clouds
// TODO: Replace above with actual icons if you have them

// Map weather description to local icon
const weatherIconMap = {
  'clear': clear_icon,
  'sunny': clear_icon,
  'clouds': clouds_icon,
  'cloudy': clouds_icon,
  'overcast': clouds_icon,
  'rain': rain_icon,
  'drizzle': drizzle_icon,
  'shower': rain_icon,
  'thunderstorm': rain_icon,
  'snow': snow_icon,
  'mist': clouds_icon,
  'fog': clouds_icon,
  'haze': clouds_icon,
  'wind': wind_icon,
};

function getLocalWeatherIcon(description) {
  if (!description) return clear_icon;
  const key = description.toLowerCase();
  // Try to match by key or by partial match
  for (const [desc, icon] of Object.entries(weatherIconMap)) {
    if (key.includes(desc)) return icon;
  }
  return clear_icon;
}

// Helper to get air quality label based on PM2.5 value
function getAirQualityLabel(pm25) {
  if (pm25 === undefined || pm25 === null) return '';
  const value = Number(pm25);
  if (value <= 12) return 'Good';
  if (value <= 35.4) return 'Moderate';
  if (value <= 55.4) return 'Unhealthy for Sensitive';
  if (value <= 150.4) return 'Unhealthy';
  if (value <= 250.4) return 'Very Unhealthy';
  return 'Hazardous';
}

const Weather = () => {
  const [locationName, setLocationName] = useState('Fetching...');
  const [weatherData, setWeatherData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showSearch, setShowSearch] = useState(false);
  const [searchValue, setSearchValue] = useState('');
  const [citySuggestions, setCitySuggestions] = useState([]);
  const popularCities = [
    'Bangalore', 'Hyderabad', 'Chennai', 'Delhi', 'Mumbai', 'Kolkata',
    'New York', 'London', 'Paris', 'Tokyo', 'Singapore', 'Sydney', 'Dubai', 'San Francisco', 'Berlin', 'Toronto', 'Cape Town', 'Moscow', 'Beijing', 'Rio de Janeiro'
  ];

  // Fetch weather data for a given city
  const fetchWeatherForCity = async (city) => {
    setLoading(true);
    setError(null);
    let backendUrl = import.meta.env.VITE_APP_URL;
    // Remove quotes if present (in case .env has VITE_APP_URL="url")
    if (backendUrl && backendUrl.startsWith('"') && backendUrl.endsWith('"')) {
      backendUrl = backendUrl.slice(1, -1);
    }
    try {
      const weatherRes = await fetch(`${backendUrl}/weather/data?city=${encodeURIComponent(city)}`);
      if (!weatherRes.ok) throw new Error('Weather fetch failed');
      const weatherJson = await weatherRes.json();
      setWeatherData(weatherJson);
      // Always update locationName to match backend's canonical name if present, else fallback to searched city
      setLocationName((weatherJson && (weatherJson.city || weatherJson.location || weatherJson.name)) || city);
      setLoading(false);
    } catch {
      setError('Could not fetch weather data');
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!showSearch) {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(async (position) => {
          const { latitude, longitude } = position.coords;
          try {
            // Using BigDataCloud Reverse Geocoding API for more reliable city results
            const response = await fetch(`https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${latitude}&longitude=${longitude}&localityLanguage=en`);
            const data = await response.json();
            // Use city or locality from BigDataCloud
            const city = data.city || data.locality || data.principalSubdivision;
            if (city) {
              setLocationName(city);
              setWeatherData(null);
              setError(null);
              setLoading(true);
              fetchWeatherForCity(city);
            } else {
              setLocationName('City not found');
              setWeatherData(null);
              setLoading(false);
            }
          } catch {
            setLocationName('Unknown');
            setWeatherData(null);
            setLoading(false);
          }
        }, () => {
          setLocationName('Location denied');
          setLoading(false);
        });
      } else {
        setLocationName('Not supported');
        setLoading(false);
      }
    }
    // ...existing code...
  }, [showSearch]);

  // Handle search submit
  const handleSearch = (e) => {
    e.preventDefault();
    if (searchValue.trim()) {
      setShowSearch(false);
      setWeatherData(null); // clear previous data
      setError(null);
      setLoading(true);
      fetchWeatherForCity(searchValue.trim());
    }
  };

  // Fetch city suggestions from GeoDB Cities API
  const fetchCitySuggestions = async (query) => {
    if (!query) {
      setCitySuggestions([]);
      return;
    }
    try {
      const res = await fetch(`https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=${encodeURIComponent(query)}&limit=5&sort=-population`, {
        headers: {
          'X-RapidAPI-Key': 'b5866957eamshce43fe21ddc5525p1e7732jsnac32a4821167', // <-- Replace with your RapidAPI key
          'X-RapidAPI-Host': 'wft-geo-db.p.rapidapi.com'
        }
      });
      if (!res.ok) throw new Error('Failed to fetch suggestions');
      const data = await res.json();
      setCitySuggestions(data.data.map(city => `${city.city}${city.country ? ', ' + city.country : ''}`));
    } catch {
      setCitySuggestions([]);
    }
  };

  // Update suggestions as user types
  const handleSearchInputChange = (e) => {
    setSearchValue(e.target.value);
    fetchCitySuggestions(e.target.value);
  };

  // Handle suggestion click
  const handleSuggestionClick = (city) => {
    setSearchValue(city);
    setShowSearch(false);
    setWeatherData(null);
    setError(null);
    setLoading(true);
    fetchWeatherForCity(city);
  };

  return (
    <div className='weather'>
      {/* <div className="search-bar">
        <input type="text" placeholder='Search'/>
        <img src={search_icon} alt="" />
      </div> */}

      <p className='location'>{locationName}</p>
      <span
        className='location-icon-wrapper'
        title='Do you want to change location?'
        style={{ cursor: 'pointer', display: 'inline-block' }}
        onClick={() => setShowSearch(true)}
      >
        <img src={location_icon} alt="" className='location-icon' />
      </span>

      {/* Search bar overlay */}
      {showSearch && (
        <div style={{ width: '100%' }}>
          <form className="search-bar" style={{ margin: '20px 0', width: '100%' }} onSubmit={handleSearch} autoComplete="off">
            <input
              type="text"
              placeholder='Enter city...'
              value={searchValue}
              onChange={handleSearchInputChange}
              autoFocus
              style={{ width: 200 }}
              list="city-suggestions"
            />
            <button type="submit" style={{ background: 'none', border: 'none', padding: 0, cursor: 'pointer' }}>
              <img src={search_icon} alt="Search" style={{ width: 50, margin: '20px 0' }} />
            </button>
          </form>
          {/* Static popular cities dropdown */}
          <div style={{ background: '#fff', borderRadius: 8, boxShadow: '0 2px 8px rgba(0,0,0,0.08)', margin: '0 auto', maxWidth: 220, padding: 8, zIndex: 10 }}>
            <div style={{ fontWeight: 'bold', color: '#333', marginBottom: 4, fontSize: 14 }}>Popular Cities</div>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: 6 }}>
              {popularCities.map(city => (
                <span key={city} style={{ cursor: 'pointer', background: '#eee', borderRadius: 12, padding: '4px 10px', marginBottom: 4, fontSize: 13 }} onClick={() => handleSuggestionClick(city)}>{city}</span>
              ))}
            </div>
          </div>
          {/* Real-time suggestions dropdown */}
          {citySuggestions.length > 0 && (
            <div style={{ background: '#fff', borderRadius: 8, boxShadow: '0 2px 8px rgba(0,0,0,0.12)', margin: '4px auto 0', maxWidth: 220, padding: 8, zIndex: 20, position: 'relative' }}>
              {citySuggestions.map((city, idx) => (
                <div key={city + idx} style={{ cursor: 'pointer', padding: '4px 0', color: '#222', fontSize: 14 }} onClick={() => handleSuggestionClick(city)}>{city}</div>
              ))}
            </div>
          )}
        </div>
      )}
      {/* Always show the UI, use backend values if available, else fallback to defaults */}
      <img
        src={weatherData ? getLocalWeatherIcon(weatherData.weatherDescription?.[0]) : clear_icon}
        alt={weatherData?.weatherDescription?.[0] || 'weather icon'}
        className='weather-icon'
      />
      <p className='temperature'>
        {loading ? '__' : (weatherData && weatherData.temperature !== undefined && weatherData.temperature !== null && weatherData.temperature !== '' ? `${weatherData.temperature}°C` : '__')}
      </p>
      <div className="weather-data">
        <div className="col">
          <img src={humidity_icon} alt="" />
          <div>
            <p>{loading ? '__' : (weatherData && weatherData.humidity !== undefined && weatherData.humidity !== null && weatherData.humidity !== '' ? `${weatherData.humidity} %` : '__')}</p>
            <span>Humidity</span>
          </div>
        </div>
        <div className="col">
          <img src={wind_icon} alt="" />
          <div>
                <p>{loading ? '__' : (weatherData && weatherData.windSpeed != null && weatherData.windSpeed !== '' ? `${Number(weatherData.windSpeed)} Km/h` : '__')}</p>
            <span>Wind Speed</span>
          </div>
        </div>
      </div>
      <div className="weather-description-center">
        <span className="weather-description-text">{loading ? '__' : (weatherData && weatherData.weatherDescription?.[0]) || '__'}</span>
      </div>
      <div className="air-quality-center">
        <span className="air-quality-label">Air Quality (PM2.5): </span>
        <span className="air-quality-value">
          {loading ? '__' : (weatherData && weatherData.pm2_5 !== undefined
            ? `${Number(weatherData.pm2_5).toFixed(1)} (${getAirQualityLabel(weatherData.pm2_5)})`
            : '__')}
        </span>
      </div>
      {/* Optionally show more: feelsLike, pressure, country, region, lat/lon, etc. */}
      {loading && <p>Loading weather...</p>}
      {error && <p style={{color: 'red'}}>{error}</p>}
    </div>
  )
}

export default Weather
