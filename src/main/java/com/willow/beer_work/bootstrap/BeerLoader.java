package com.willow.beer_work.bootstrap;

import com.willow.beer_work.domain.Beer;
import com.willow.beer_work.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {
    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository){
        this.beerRepository = beerRepository;
    }
    @Override
    public void run(String... args) {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                        .beerName("La Virgen")
                        .beerStyle("IPA")
                        .quantityToBrew(300)
                        .upc(13432423L)
                        .price(new BigDecimal("6.5"))
                        .minOnHand(30)
                        .build()
            );

            beerRepository.save(Beer.builder()
                    .beerName("Paulaner")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(400)
                    .upc(13432424L)
                    .price(new BigDecimal("7.6"))
                    .minOnHand(10)
                    .build()
            );
        }
    }
}
