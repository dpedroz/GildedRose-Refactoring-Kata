package com.gildedrose;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class GildedRoseTest {

    Item normalItem() { return new Item("+5 Dexterity Vest", 10, 20); }
    Item agedBrie() { return new Item("Aged Brie", 2, 0); }
    Item sulfuras() { return new Item("Sulfuras, Hand of Ragnaros", 5, 80); }
    Item backstagePass() { return new Item("Backstage passes to a TAFKAL80ETC concert", 12, 20); }
    Item conjured() { return new Item("Conjured", 5, 30); }

    @Test
    public void updateNormalItemOnce() {
        GildedRose app = updateInventory(normalItem(), 1);
        assertEquals("+5 Dexterity Vest", app.items[0].name);
        assertEquals(9, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }

    @Test
    public void updateNormalItemAfterSellinDegrades() {
        GildedRose app = updateInventory(normalItem(), 12);
        assertEquals("+5 Dexterity Vest", app.items[0].name);
        assertEquals(-2, app.items[0].sellIn);
        assertEquals(6, app.items[0].quality);
    }

    @Test
    public void updateAgedBrieOnce() {
        GildedRose app = updateInventory(agedBrie(), 1);
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(1, app.items[0].sellIn);
        assertEquals(1, app.items[0].quality);
    }

    @Test
    public void updateAgedBrieAfterSellinDegrades() {
        GildedRose app = updateInventory(agedBrie(), 6);
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(-4, app.items[0].sellIn);
        assertEquals(10, app.items[0].quality);
    }

    @Test
    public void updateSulfuras() {
        GildedRose app = updateInventory(sulfuras(), 1);
        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name);
        assertEquals(5, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    public void updateBackstagePass() {
        GildedRose app = updateInventory(backstagePass(), 1);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(11, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    @Test
    public void updateBackstagePassWhenSellinLessThan10() {
        GildedRose app = updateInventory(backstagePass(), 3);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(9, app.items[0].sellIn);
        assertEquals(24, app.items[0].quality);
    }

    @Test
    public void updateBackstagePassWhenSellinLessThan5() {
        GildedRose app = updateInventory(backstagePass(), 8);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(4, app.items[0].sellIn);
        assertEquals(35, app.items[0].quality);
    }

    @Test
    public void updateBackstagePassWhenAfterConcert() {
        GildedRose app = updateInventory(backstagePass(), 13);
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    public void updateConjured() {
        GildedRose app = updateInventory(conjured(), 1);
        assertEquals("Conjured", app.items[0].name);
        assertEquals(4, app.items[0].sellIn);
        assertEquals(28, app.items[0].quality);
    }

    @Test
    public void updateConjuredAfterSellinDegrades() {
        GildedRose app = updateInventory(conjured(), 6);
        assertEquals("Conjured", app.items[0].name);
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(16, app.items[0].quality);
    }

    @Test
    public void qualityShouldDegradeTwiceAsFastAfterSellinDate() {
        GildedRose app = updateInventory(normalItem(), 10);
        assertEquals(10, app.items[0].quality);
        app.updateQuality();
        assertEquals(8, app.items[0].quality);
    }

    @Test
    public void qualityShouldNeverBeNegative() {
        GildedRose app = updateInventory(normalItem(), 25);
        assertEquals(0, app.items[0].quality);
    }

    private GildedRose updateInventory(Item item, int days) {
        Item[] items = new Item[] { item };
        GildedRose app = new GildedRose(items);
        Stream.iterate(0, i -> i).limit(days).forEach(i -> app.updateQuality() );
        return app;
    }

}
