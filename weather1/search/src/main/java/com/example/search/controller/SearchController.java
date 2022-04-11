package com.example.search.controller;

import com.example.details.exception.NotFoundException;
import com.example.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/weather/search")
    public ResponseEntity<?> getDetails(@RequestParam List<String> cities) {
        //TODO
        logger.trace("getDetails");
        if(cities == null) {
            throw new NotFoundException("'CITY IS EMPTY");
        }
        return new ResponseEntity<List<String>>(searchService.getDetails(cities), HttpStatus.OK);
    }
}
