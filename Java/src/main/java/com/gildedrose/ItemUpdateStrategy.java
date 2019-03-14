package com.gildedrose;

public class ItemUpdateStrategy {

    private ItemUpdateStrategy() {}

    public static UpdateStrategy forName(String name) {
        switch (name) {
            case "Aged Brie":                                   return new ItemUpdateStrategy().new CheeseUpdate();
            case "Sulfuras, Hand of Ragnaros":                  return new ItemUpdateStrategy().new LegendaryUpdate();
            case "Backstage passes to a TAFKAL80ETC concert":   return new ItemUpdateStrategy().new ConcertTicket();
            case "Conjured":                                    return new ItemUpdateStrategy().new Conjured();
            default:                                            return new ItemUpdateStrategy().new DefaultUpdate();
        }
    }

    private class DefaultUpdate implements UpdateStrategy {
        public void update(Item item) {
            decreaseSellin(item);
            decreaseQuality(item);
        }
        void decreaseSellin(Item item) {
            item.sellIn = item.sellIn - 1;
        }
        void decreaseQuality(Item item) {
            item.quality = Math.max(0, item.quality - (item.sellIn < 0 ? 2 : 1));
        }
        void increaseQuality(Item item) {
            item.quality = Math.min(50, item.quality + (item.sellIn < 0 ? 2 : 1));
        }
    }

    private class CheeseUpdate extends DefaultUpdate {
        @Override
        public void update(Item item) {
            decreaseSellin(item);
            increaseQuality(item);
        }
    }

    private class LegendaryUpdate implements UpdateStrategy {
        @Override
        public void update(Item item) {}

    }
    private class ConcertTicket extends DefaultUpdate {
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

    private class Conjured extends DefaultUpdate {
        @Override
        public void decreaseQuality(Item item) {
            item.quality = Math.max(0, item.quality - (item.sellIn < 0 ? 4 : 2));
        }
    }
}

interface UpdateStrategy {
    void update(Item item);
}




