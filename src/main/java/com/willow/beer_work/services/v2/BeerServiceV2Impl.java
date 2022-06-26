package com.willow.beer_work.services.v2;

import com.willow.beer_work.web.model.v2.BeerDtoV2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceV2Impl implements BeerServiceV2 {
    @Override
    public BeerDtoV2 getBeerById(UUID beerId) {
        return null;
    }

    @Override
    public BeerDtoV2 save(BeerDtoV2 beerDto) {
        return null;
    }

    @Override
    public void update(UUID beerId, BeerDtoV2 beerDtoV2) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }
}
