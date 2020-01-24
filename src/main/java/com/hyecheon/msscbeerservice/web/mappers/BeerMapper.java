package com.hyecheon.msscbeerservice.web.mappers;

import com.hyecheon.msscbeerservice.domain.Beer;
import com.hyecheon.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

/**
 * @author hyecheon
 * @date 2020-01-24
 */
@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

  BeerDto beerToBeerDto(Beer beer);

  Beer beerDtoToBeer(BeerDto dto);

}
