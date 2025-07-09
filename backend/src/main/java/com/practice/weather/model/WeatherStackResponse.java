package com.practice.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherStackResponse {

    public Current getCurrentSafe(){
        return this.current;
    }

    public Location getLocationSafe(){
        return this.location;
    }

    @JsonProperty("current")
    private Current current;

    @JsonProperty("location")
    private Location location;

    public Current getCurrent(){
        return current;
    }

    public void setCurrent(Current current){
        this.current = current;
    }

    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }






// public static class Current{
//     @JsonProperty("temperature")
//     private Double temperature;

//     public Double getTemperature(){
//         return temperature;
//     }

//     public void setTemperature(Double temp){
//         this.temperature = temp;
//     }
// }


@JsonIgnoreProperties(ignoreUnknown = true)
public static class Current{

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("feelslike")
    private Integer feelsLike;

    @JsonProperty("wind_speed")
    private Integer windSpeed;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("weather_descriptions")
    private List<String> weatherDescriptions;

    @JsonProperty("weather_icons")
    private List<String> weatherIcons;

    @JsonProperty("pm2_5")
    private Double pm2_5;   // Just in case if pm2.5 is outside the air quality 

    @JsonProperty("air_quality")
    private Airquality airquality;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Airquality{

        @JsonProperty("pm2_5")
        private Double pm2_5;

        @JsonProperty("pm10")
        private Double pm10;

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

    public Airquality getAirquality() {
        return airquality;
    }

    public void setAirquality(Airquality airquality) {
        this.airquality = airquality;
    }

    



}

@JsonIgnoreProperties(ignoreUnknown=true)
public static class Location{

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("region")
    private String region;

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("lon")
    private Double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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




}
