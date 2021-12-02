package com.gildedrose;

public class Item {
	public int id;
	
    public String name;

    public int sellIn;

    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }
    public Item(int id, String name, int sellIn, int quality) {
    	this(name, sellIn, quality);
        this.id = id;
    }

   @Override
   public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }

}
