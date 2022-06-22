package com.willow.beer_work.services;

import com.willow.beer_work.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID()).beerName("Black Beer").beerStyle("James Joyce").build();
    }

    @Override
    public BeerDto save(BeerDto beerDto) {
        return BeerDto.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void update(UUID beerId, BeerDto beerDto) {
        // TODO impl - would add a real impl to update beer
    }

    @Override
    public void deleteById(UUID beerId) {
       log.debug("Deleting a beer...");
    }


}
