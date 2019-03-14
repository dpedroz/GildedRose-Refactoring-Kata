package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {

        for (Item item : items) {
            updateStrategyFor(item.name).update(item);
        }

    }

    private UpdateStrategy updateStrategyFor(String name) {
        switch (name) {
            case "Aged Brie":                                   return new CheeseUpdate();
            case "Sulfuras, Hand of Ragnaros":                  return new LegendaryUpdate();
            case "Backstage passes to a TAFKAL80ETC concert":   return new ConcertTicket();
            case "Conjured":                                    return new Conjured();
            default:                                            return new DefaultUpdate();
        }
    }
}

class CheeseUpdate implements UpdateStrategy {
    @Override
    public void update(Item item) {
        decreaseSellin(item);
        increaseQuality(item);
    }
}

class LegendaryUpdate implements UpdateStrategy {
    @Override
    public void update(Item item) {}
}

class ConcertTicket implements UpdateStrategy {
    @Override
    public void decreaseQuality(Item item) {
        int qualityChange = item.sellIn >= 10 ? 1
                            : item.sellIn >= 5 ? 2
                              : item.sellIn >= 0 ? 3
                                : item.quality * -1;
        item.quality = item.quality +  qualityChange;
        item.quality = Math.min(item.quality, 50);
    }
}

class DefaultUpdate implements UpdateStrategy {}

class Conjured implements UpdateStrategy {
    @Override
    public void decreaseQuality(Item item) {
        item.quality = Math.max(0, item.quality - (item.sellIn < 0 ? 4 : 2));
    }
}

interface UpdateStrategy {
    default void update(Item item) {
        decreaseSellin(item);
        decreaseQuality(item);
    }
    default void decreaseSellin(Item item) {
        item.sellIn = item.sellIn - 1;
    }
    default void decreaseQuality(Item item) {
        item.quality = Math.max(0, item.quality - (item.sellIn < 0 ? 2 : 1));
    }
    default void increaseQuality(Item item) {
        item.quality = Math.min(50, item.quality + (item.sellIn < 0 ? 2 : 1));
    }
}

