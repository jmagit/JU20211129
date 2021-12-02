package com.gildedrose;

import com.gildedrose.core.InvalidDataException;
import com.gildedrose.core.NotFoundException;

public class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
    	for (Item item: items) {
    		if(item.name.equals("Sulfuras, Hand of Ragnaros")) {
    			if(item.quality != 80)
    				throw new IllegalArgumentException();
    		} else if(item.quality < 0 || item.quality > 50)
    				throw new IllegalArgumentException();
		}
        this.items = items;
    }

    ItemRepository dao;
    
    public GildedRose(ItemRepository dao) throws Exception {
    	this.dao = dao;
        this.items = dao.getAll().toArray(new Item[] {});
    }
    
    public void save() throws NotFoundException, InvalidDataException {
    	for(Item item: items)
    		dao.modify(item);
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }
    }

}
