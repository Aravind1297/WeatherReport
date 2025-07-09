package com.practice.weather.model;

//import com.practice.weather.service.WeatherStackService;

public class WeatherCacheEntry {
    private WeatherStackResponse response;
    private long timestamp;

    public WeatherCacheEntry(WeatherStackResponse response, long timestamp){
        this.response = response;
        this.timestamp = timestamp;
    }

    public WeatherStackResponse getResponse(){
        return response;
    }

    public long getTimeStamp(){
        return timestamp;
    }

}
