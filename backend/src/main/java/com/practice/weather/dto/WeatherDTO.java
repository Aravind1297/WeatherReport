package com.practice.weather.dto;
import java.util.List;
import com.practice.weather.model.WeatherStackResponse;
public class WeatherDTO{

    private String city;
    private Double temperature;
    private Integer humidity;
    private Integer feelsLike;
    private Integer windSpeed;
    private Integer pressure;
    private List<String> weatherDescriptions;
    private List<String> weatherIcons;
    private Double pm2_5;
    private Double pm10;

   // private String name; //name of the city - No idea why external api response is returned as name instead of city
    private String country;
    private String region;
    private Double latitude;
    private Double longitude;


    public WeatherDTO(WeatherStackResponse weatherStackResponse){
        this.city = weatherStackResponse.getLocationSafe().getName();
        this.temperature = weatherStackResponse.getCurrentSafe().getTemperature();
        this.humidity = weatherStackResponse.getCurrentSafe().getHumidity();
        this.feelsLike = weatherStackResponse.getCurrentSafe().getFeelsLike();
        this.windSpeed = weatherStackResponse.getCurrentSafe().getWindSpeed();
        this.pressure = weatherStackResponse.getCurrentSafe().getPressure();
        this.weatherDescriptions = weatherStackResponse.getCurrentSafe().getWeatherDescriptions();
        this.weatherIcons = weatherStackResponse.getCurrentSafe().getWeatherIcons();

        if(weatherStackResponse.getCurrentSafe().getAirquality() !=null){
            this.pm2_5 = weatherStackResponse.getCurrentSafe().getAirquality().getPm2_5();
            this.pm10 = weatherStackResponse.getCurrentSafe().getAirquality().getPm10();

        }
        
        this.country = weatherStackResponse.getLocationSafe().getCountry();
        this.region = weatherStackResponse.getLocationSafe().getRegion();
        this.latitude = weatherStackResponse.getLocationSafe().getLatitude();
        this.longitude = weatherStackResponse.getLocationSafe().getLongitude();

    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public Double getTemperature() {
        return temperature;
    }


    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }


    public Integer getHumidity() {
        return humidity;
    }


    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }


    public Integer getFeelsLike() {
        return feelsLike;
    }


    public void setFeelsLike(Integer feelsLike) {
        this.feelsLike = feelsLike;
    }


    public Integer getWindSpeed() {
        return windSpeed;
    }


    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }


    public Integer getPressure() {
        return pressure;
    }


    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }


    public List<String> getWeatherDescriptions() {
        return weatherDescriptions;
    }


    public void setWeatherDescriptions(List<String> weatherDescriptions) {
        this.weatherDescriptions = weatherDescriptions;
    }


    public List<String> getWeatherIcons() {
        return weatherIcons;
    }


    public void setWeatherIcons(List<String> weatherIcons) {
        this.weatherIcons = weatherIcons;
    }


    public Double getPm2_5() {
        return pm2_5;
    }


    public void setPm2_5(Double pm2_5) {
        this.pm2_5 = pm2_5;
    }


    public Double getPm10() {
        return pm10;
    }


    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getRegion() {
        return region;
    }


    public void setRegion(String region) {
        this.region = region;
    }


    public Double getLatitude() {
        return latitude;
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    


    

    
}
