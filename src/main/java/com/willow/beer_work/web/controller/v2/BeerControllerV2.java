package com.willow.beer_work.web.controller.v2;

import com.willow.beer_work.services.v2.BeerServiceV2;
import com.willow.beer_work.web.model.v2.BeerDtoV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {
    private final BeerServiceV2 beerServiceV2;

    public BeerControllerV2(BeerServiceV2 beerServiceV2) {
        this.beerServiceV2 = beerServiceV2;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable UUID beerId){
        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDtoV2> save(@RequestBody BeerDtoV2 beerDtoV2){
        BeerDtoV2 dto = beerServiceV2.save(beerDtoV2);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v2/beer/" + dto.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> update(@PathVariable UUID beerId, @RequestBody BeerDtoV2 beerDtoV2){
        beerServiceV2.update(beerId, beerDtoV2);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID beerId){
        beerServiceV2.deleteById(beerId);
    }
}
