package com.gildedrose

class Item(val name: String, var sellIn: Int, var quality: Int) {

  def updateQuality() {
    name match {

      case "Aged Brie" =>
        sellIn = sellIn -1
        quality = quality + (if (sellIn < 0) 2 else 1)
        quality = math.min(50, quality)

      case "Sulfuras, Hand of Ragnaros" =>

      case "Backstage passes to a TAFKAL80ETC concert" =>
        sellIn = sellIn - 1
        quality = quality + (sellIn match {
          case s: Int if s >= 10 => 1
          case s: Int if s >= 5 => 2
          case s: Int if s >= 0 => 3
          case _ => -1 * quality
        })
        quality = math.min(50, quality)

      case _ =>
        sellIn = sellIn - 1
        quality = math.max(0, quality - (if (sellIn < 0) 2 else 1))

    }
  }

}
