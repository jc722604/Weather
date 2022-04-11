package com.example.search.service;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.websocket.EndpointConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService{
    private final RestTemplate restTemplate;
    private final String url = String.format("http://localhost:8200/details");

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getDetails(List<String> cities) {
        CompletableFuture<List<Map<String, Map>>>[] futures = new CompletableFuture[cities.size()];
        for(String city: cities) {
            CompletableFuture<List<Map<String, Map>>> completableFuture = CompletableFuture.supplyAsync(() ->
                    getCityDetails(city));
        }
        return cities;
    }


    public List<Map<String, Map>> getCityDetails(String city) {
        int[] cityId = restTemplate.getForObject(url + city, int[].class);
        CompletableFuture<Map<String, Map>>[] futures = new CompletableFuture[cityId.length];

        for(int cityID : cityId) {
            CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
                HashMap detail = restTemplate.getForObject(url + "->" + cityID, HashMap.class);
                return detail;
            });
        }
        return CompletableFuture.allOf(futures).thenApplyAsync(f -> {
            return Arrays.asList(futures).stream().map(s -> s.join()).collect(Collectors.toList());
        }).join();
    }
}
