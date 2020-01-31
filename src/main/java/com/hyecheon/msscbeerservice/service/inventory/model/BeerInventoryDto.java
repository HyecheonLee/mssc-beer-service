package com.hyecheon.msscbeerservice.service.inventory.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hyecheon
 * @date 2020-01-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerInventoryDto {

  private UUID id;
  private OffsetDateTime createdDate;
  private OffsetDateTime lastModifiedDate;
  private UUID beerId;
  private Integer quantityOnHand;
}