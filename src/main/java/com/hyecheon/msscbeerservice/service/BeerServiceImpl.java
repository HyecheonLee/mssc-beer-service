package com.hyecheon.msscbeerservice.service;

import com.hyecheon.msscbeerservice.domain.Beer;
import com.hyecheon.msscbeerservice.respository.BeerRepository;
import com.hyecheon.msscbeerservice.web.controller.NotFoundException;
import com.hyecheon.msscbeerservice.web.mappers.BeerMapper;
import com.hyecheon.msscbeerservice.web.model.BeerDto;
import com.hyecheon.msscbeerservice.web.model.BeerPagedList;
import com.hyecheon.msscbeerservice.web.model.BeerStyleEnum;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author hyecheon
 * @date 2020-01-29
 */
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
  @Override
  public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest,
      Boolean showInventoryOnHand) {

    BeerPagedList beerPagedList;
    Page<Beer> beerPage;

    if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
      //search both
      beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
    } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
      //search beer_service name
      beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
    } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
      //search beer_service style
      beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    } else {
      beerPage = beerRepository.findAll(pageRequest);
    }

    if (showInventoryOnHand) {
      beerPagedList = new BeerPagedList(beerPage
          .getContent()
          .stream()
          .map(beerMapper::beerToBeerDtoWithInventory)
          .collect(Collectors.toList()),
          PageRequest
              .of(beerPage.getPageable().getPageNumber(),
                  beerPage.getPageable().getPageSize()),
          beerPage.getTotalElements());
    } else {
      beerPagedList = new BeerPagedList(beerPage
          .getContent()
          .stream()
          .map(beerMapper::beerToBeerDto)
          .collect(Collectors.toList()),
          PageRequest
              .of(beerPage.getPageable().getPageNumber(),
                  beerPage.getPageable().getPageSize()),
          beerPage.getTotalElements());
    }

    return beerPagedList;
  }

  @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false ")
  @Override
  public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
    if (showInventoryOnHand) {
      return beerMapper.beerToBeerDtoWithInventory(
          beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
      );
    } else {
      return beerMapper.beerToBeerDto(
          beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
      );
    }
  }

  @Override
  public BeerDto saveNewBeer(BeerDto beerDto) {
    return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
  }

  @Override
  public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
    Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

    beer.setBeerName(beerDto.getBeerName());
    beer.setBeerStyle(beerDto.getBeerStyle().name());
    beer.setPrice(beerDto.getPrice());
    beer.setUpc(beerDto.getUpc());

    return beerMapper.beerToBeerDto(beerRepository.save(beer));
  }

  @Cacheable(cacheNames = "beerUpcCache")
  @Override
  public BeerDto getByUpc(String upc) {
    return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
  }
}
