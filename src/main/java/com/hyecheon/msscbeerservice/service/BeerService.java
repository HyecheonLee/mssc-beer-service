package com.hyecheon.msscbeerservice.service;

import com.hyecheon.msscbeerservice.web.model.BeerDto;
import java.util.UUID;

/**
 * @author hyecheon
 * @date 2020-01-29
 */
public interface BeerService {

  BeerDto getById(UUID beerId);

  BeerDto savedNewBeer(BeerDto beerDto);

  BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
