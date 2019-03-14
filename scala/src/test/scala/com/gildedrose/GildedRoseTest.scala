package com.gildedrose

import org.scalatest._

class GildedRoseSpec extends FlatSpec with Matchers {

  behavior of "GildedRose"

  it should "properly update normal item" in new Fixture {
    var items = Array[Item](normal)
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0).name should equal("+5 Dexterity Vest")
    app.items(0).sellIn should equal(9)
    app.items(0).quality should equal(19)
  }

  it should "properly update normal item after sellIn degrades" in new Fixture {
    var items = Array[Item](normal)
    val app = new GildedRose(items)
    for (_ <- 1 to 12) { app.updateQuality() }
    app.items(0).name should equal("+5 Dexterity Vest")
    app.items(0).sellIn should equal(-2)
    app.items(0).quality should equal(6)
  }

  it should "properly update Brie" in new Fixture {
    var items = Array[Item](brie)
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0).name should equal("Aged Brie")
    app.items(0).sellIn should equal(1)
    app.items(0).quality should equal(1)
  }

  it should "properly update Brie after sellIn degrades" in new Fixture {
    var items = Array[Item](brie)
    val app = new GildedRose(items)
    for (_ <- 1 to 6) { app.updateQuality() }
    app.items(0).name should equal("Aged Brie")
    app.items(0).sellIn should equal(-4)
    app.items(0).quality should equal(10)
  }

  it should "properly update Sulfuras" in new Fixture {
    var items = Array[Item](sulfuras)
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0).name should equal("Sulfuras, Hand of Ragnaros")
    app.items(0).sellIn should equal(5)
    app.items(0).quality should equal(80)
  }

  it should "properly update Sulfuras after sellIn degrades" in new Fixture {
    var items = Array[Item](sulfuras)
    val app = new GildedRose(items)
    for (_ <- 1 to 6) { app.updateQuality() }
    app.items(0).name should equal("Sulfuras, Hand of Ragnaros")
    app.items(0).sellIn should equal(5)
    app.items(0).quality should equal(80)
  }

  it should "properly update Backstage pass" in new Fixture {
    var items = Array[Item](backstagePass)
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0).name should equal("Backstage passes to a TAFKAL80ETC concert")
    app.items(0).sellIn should equal(11)
    app.items(0).quality should equal(21)
  }

  it should "properly update Backstage pass when  5 < sellin <= 10 " in new Fixture {
    var items = Array[Item](backstagePass)
    val app = new GildedRose(items)
    for (_ <- 1 to 3) { app.updateQuality() }
    app.items(0).name should equal("Backstage passes to a TAFKAL80ETC concert")
    app.items(0).sellIn should equal(9)
    app.items(0).quality should equal(24)
  }

  it should "properly update Backstage pass when  sellin <= 5 " in new Fixture {
    var items = Array[Item](backstagePass)
    val app = new GildedRose(items)
    for (_ <- 1 to 8) { app.updateQuality() }
    app.items(0).name should equal("Backstage passes to a TAFKAL80ETC concert")
    app.items(0).sellIn should equal(4)
    app.items(0).quality should equal(35)
  }

  it should "properly update Backstage pass when sellin <0  " in new Fixture {
    var items = Array[Item](backstagePass)
    val app = new GildedRose(items)
    for (_ <- 1 to 13) { app.updateQuality() }
    app.items(0).name should equal("Backstage passes to a TAFKAL80ETC concert")
    app.items(0).sellIn should equal(-1)
    app.items(0).quality should equal(0)
  }

  it should "properly update Conjured" in new Fixture {
    var items = Array[Item](conjured)
    val app = new GildedRose(items)
    app.updateQuality()
    app.items(0).name should equal("Conjured")
    app.items(0).sellIn should equal(4)
    app.items(0).quality should equal(28)
  }

  it should "properly update Conjured after sellIn degrades" in new Fixture {
    var items = Array[Item](conjured)
    val app = new GildedRose(items)
    for (_ <- 1 to 6) { app.updateQuality() }
    app.items(0).name should equal("Conjured")
    app.items(0).sellIn should equal(-1)
    app.items(0).quality should equal(16)
  }

  it should "Once the sell by date has passed, Quality degrades twice as fast" in new Fixture {
    var items = Array[Item](normal)
    val app = new GildedRose(items)
    for (_ <- 1 to 10) { app.updateQuality() }
    app.items(0).quality should equal(10)
    app.updateQuality()
    app.items(0).quality should equal(8)
  }

  it should "The Quality of an item is never negative" in new Fixture {
    var items = Array[Item](normal)
    val app = new GildedRose(items)
    for (_ <- 1 to 25) { app.updateQuality() }
    app.items(0).quality should equal(0)
  }

  class Fixture {
    val normal = new Item("+5 Dexterity Vest", 10, 20)
    val brie = new Item("Aged Brie", 2, 0)
    val sulfuras = new Item("Sulfuras, Hand of Ragnaros", 5, 80)
    val backstagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 12, 20)
    val conjured = new Item("Conjured", 5, 30)
  }
}
