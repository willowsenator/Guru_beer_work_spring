package com.willow.beer_work.services.v2;

import com.willow.beer_work.web.model.v2.BeerDtoV2;
import com.willow.beer_work.web.model.v2.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceV2Impl implements BeerServiceV2 {
    @Override
    public BeerDtoV2 getBeerById(UUID beerId) {
        return BeerDtoV2.builder().id(UUID.randomUUID()).beerName("Black Beer").beerStyle(BeerStyleEnum.IPA).build();
    }

    @Override
    public BeerDtoV2 save(BeerDtoV2 beerDtoV2) {
        return BeerDtoV2.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public void update(UUID beerId, BeerDtoV2 beerDtoV2) {
        // TODO impl - would add a real impl to update beer
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("Deleting a beer...");
    }


}
