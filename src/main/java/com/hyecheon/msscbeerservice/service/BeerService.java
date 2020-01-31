package com.hyecheon.msscbeerservice.service;

import com.hyecheon.msscbeerservice.web.model.BeerDto;
import com.hyecheon.msscbeerservice.web.model.BeerPagedList;
import com.hyecheon.msscbeerservice.web.model.BeerStyleEnum;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;

/**
 * @author hyecheon
 * @date 2020-01-29
 */
public interface BeerService {

  BeerPagedList listBeers(String beerName,
      BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

  BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

  BeerDto saveNewBeer(BeerDto beerDto);

  BeerDto updateBeer(UUID beerId, BeerDto beerDto);

  BeerDto getByUpc(String upc);
}
