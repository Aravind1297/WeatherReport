package com.practice.weather.service;

import com.practice.weather.model.WeatherStackResponse;
import com.practice.weather.model.WeatherCacheEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;


@Service
public class WeatherStackService {

    @Value("${weatherstack.api.key}")
    private String WeatherStackApiKey;

    @Value("${weatherstack.api.url}")
    private String WeatherStackApiUrl;

    //internal cache
    private final ConcurrentHashMap<String, WeatherCacheEntry> cache = new ConcurrentHashMap<>();
    private static final long CACHE_DURATION = 60 * 60 * 1000; // 1 hour cache duration
    // @Value("${weather.cache.duration.ms}")
    // private static final long CACHE_DURATION;

    public WeatherStackResponse getWeatherData(String city){
        city = city.trim().toLowerCase();
        long now = System.currentTimeMillis();

        if(cache.containsKey(city)){
            WeatherCacheEntry entry = cache.get(city);
            if((now - entry.getTimeStamp()) < CACHE_DURATION){
                System.out.println("Served from Cache for "+city);
                return entry.getResponse();
            }
            else{
                cache.remove(city);
            }
        }
        String url = String.format("%s/current?access_key=%s&query=%s",WeatherStackApiUrl,
                                    WeatherStackApiKey,city);

        RestTemplate restTemplate = new RestTemplate();

        WeatherStackResponse response = restTemplate.getForObject(url, WeatherStackResponse.class);

        if(response !=null && response.getCurrentSafe() !=null){
            cache.put(city,new WeatherCacheEntry(response,now));
            System.out.println("Called WeatherStack API for "+city);
            return response;
        }

        return null;


    }




    // public Double getTemperature(String city){
    //     String url = String.format("%s/current?access_key=%s&query=%s",WeatherStackApiUrl,
    //                                 WeatherStackApiKey,city);
        

    //     RestTemplate restTemplate = new RestTemplate();

    //     //System.out.println("Calling API :" + url);
        
    //     WeatherStackResponse response = restTemplate.getForObject(url, WeatherStackResponse.class);
    //     //System.out.println("API Response : "+response);
    //     return response !=null ? response.getCurrent().getTemperature() : null; 
    // }


}
