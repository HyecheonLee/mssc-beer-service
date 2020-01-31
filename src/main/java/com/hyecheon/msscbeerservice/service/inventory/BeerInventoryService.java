package com.hyecheon.msscbeerservice.service.inventory;

import java.util.UUID;

/**
 * @author hyecheon
 * @date 2020-01-30
 */
public interface BeerInventoryService {
  Integer getOnhandInventory(UUID beerId);
}
