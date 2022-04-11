package com.example.details.service;



import com.example.details.config.EndpointConfig;
import com.example.details.pojo.City;
import com.example.details.pojo.CityWeather;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final RestTemplate restTemplate;


    public WeatherServiceImpl(RestTemplate getRestTemplate) {
        this.restTemplate = getRestTemplate;
    }

    @Override
    @Retryable(include = IllegalAccessError.class)
    public List<Integer> findCityIdByName(String city) {
        City[] cities = restTemplate.getForObject(EndpointConfig.queryWeatherByCity + city, City[].class);
        List<Integer> ans = new ArrayList<>();
        for(City c: cities) {
            if(c != null && c.getWoeid() != null) {
                ans.add(c.getWoeid());
            }
        }
        return ans;
    }

    @Override
    //change findcitynamebyid => find weather details by id
    public Map<String, Map> findCityNameById(int id) {
        Map<String, Map> ans = restTemplate.getForObject(EndpointConfig.queryWeatherById + id, HashMap.class);

        return ans;
    }

    @Override
    public List<CityWeather> findCityWeather(String city) {
        return findCityIdByName(city).stream()
                .map(this :: findWeatherById)
                .map(s -> s.join())
                .collect(Collectors.toList());
    }


    // find weather details by id
    public CompletableFuture<CityWeather> findWeatherById(int id) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(EndpointConfig.queryWeatherById + id, CityWeather.class));
    }
}
