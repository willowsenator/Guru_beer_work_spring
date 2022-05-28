package com.willow.beer_work.services;

import com.willow.beer_work.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID()).name("Black Beer").style("James Joyce").build();
    }
}
