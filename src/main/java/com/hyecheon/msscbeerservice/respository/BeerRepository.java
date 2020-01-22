package com.hyecheon.msscbeerservice.respository;

import com.hyecheon.msscbeerservice.domain.Beer;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
