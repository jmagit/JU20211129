package com.gildedrose.domains.contracts;

import com.gildedrose.core.Repository;
import com.gildedrose.domains.entities.Item;

public interface ItemService extends Repository<Item, Integer> {
	void updateQuality() throws Exception;
}
