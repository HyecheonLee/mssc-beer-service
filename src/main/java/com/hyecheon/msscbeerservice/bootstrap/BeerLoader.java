package com.hyecheon.msscbeerservice.bootstrap;

import com.hyecheon.msscbeerservice.domain.Beer;
import com.hyecheon.msscbeerservice.respository.BeerRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {

  private final BeerRepository beerRepository;

  @Override
  public void run(String... args) throws Exception {
    loadBeerObjects();
  }

  private void loadBeerObjects() {
    if (beerRepository.count() == 0) {
      beerRepository.save(Beer.builder()
          .beerName("Mango Bobs")
          .beerStyle("IPA")
          .quantityToBrew(200)
          .minOnHand(12)
          .upc(33701000001L)
          .price(new BigDecimal("12.95"))
          .build());
      beerRepository.save(Beer.builder()
          .beerName("Galaxy Cat")
          .beerStyle("PALE_ALE")
          .quantityToBrew(200)
          .minOnHand(12)
          .upc(33701000002L)
          .price(new BigDecimal("11.95"))
          .build());
    }
    System.out.println("Loaded Beers: " + beerRepository.count());
  }
}
