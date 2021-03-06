package com.hyecheon.msscbeerservice.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyecheon.msscbeerservice.bootstrap.BeerLoader;
import com.hyecheon.msscbeerservice.service.BeerService;
import com.hyecheon.msscbeerservice.web.model.BeerDto;
import com.hyecheon.msscbeerservice.web.model.BeerStyleEnum;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @MockBean
  BeerService beerService;

  @Test
  void getBeerById() throws Exception {
    given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());
    mockMvc.perform(
        get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("v1/beer", pathParameters(
            parameterWithName("beerId").description("UUID of desired beer to get.")
        )));
  }

  @Test
  void saveNewBeer() throws Exception {

    final var beerDto = getValidBeerDto();
    final var beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());

    mockMvc.perform(post("/api/v1/beer")
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
        .andExpect(status().isCreated());
  }

  @Test
  void updateBeerById() throws Exception {
    final var beerDto = getValidBeerDto();
    final var beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDto());

    mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
        .contentType(MediaType.APPLICATION_JSON)
        .content(beerDtoJson))
        .andExpect(status().isNoContent());
  }

  BeerDto getValidBeerDto() {
    return BeerDto.builder()
        .beerName("My Beer")
        .beerStyle(BeerStyleEnum.ALE)
        .price(new BigDecimal("2.99"))
        .upc(BeerLoader.BEER_1_UPC)
        .build();
  }
}