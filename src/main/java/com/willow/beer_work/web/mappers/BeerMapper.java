package com.willow.beer_work.web.mappers;

import com.willow.beer_work.domain.Beer;
import com.willow.beer_work.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDto dto);
    BeerDto beerToBeerDto(Beer beer);
}
