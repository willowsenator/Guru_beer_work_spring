package com.willow.beer_work.services;

import com.willow.beer_work.web.model.BeerDto;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    BeerDto save(BeerDto beerDto);
}
