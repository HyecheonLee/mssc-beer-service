package com.hyecheon.msscbeerservice.respository;

import com.hyecheon.msscbeerservice.domain.Beer;
import com.hyecheon.msscbeerservice.web.model.BeerStyleEnum;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

  Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

  Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);

  Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle,
      Pageable pageable);

  Beer findByUpc(String upc);
}
