package com.willow.beer_work.services.v2;

import com.willow.beer_work.web.model.v2.BeerDtoV2;

import java.util.UUID;

public interface BeerServiceV2 {
    BeerDtoV2 getBeerById(UUID beerId);

    BeerDtoV2 save(BeerDtoV2 beerDto);

    void update(UUID beerId, BeerDtoV2 beerDtoV2);

    void deleteById(UUID beerId);
}
