package com.hyecheon.msscbeerservice.service;

import com.hyecheon.msscbeerservice.respository.BeerRepository;
import com.hyecheon.msscbeerservice.web.controller.NotFoundException;
import com.hyecheon.msscbeerservice.web.mappers.BeerMapper;
import com.hyecheon.msscbeerservice.web.model.BeerDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hyecheon
 * @date 2020-01-29
 */
@Transactional
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public BeerDto getById(UUID beerId) {
    return beerMapper
        .beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
  }

  @Override
  public BeerDto savedNewBeer(BeerDto beerDto) {
    return beerMapper.beerToBeerDto(
        beerRepository.save(
            beerMapper.beerDtoToBeer(beerDto))
    );
  }

  @Override
  public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
    final var beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
    beer.setBeerName(beerDto.getBeerName());
    beer.setBeerStyle(beerDto.getBeerStyle().name());
    beer.setPrice(beerDto.getPrice());
    beer.setUpc(beerDto.getUpc());
    return beerMapper.beerToBeerDto(beer);
  }
}
