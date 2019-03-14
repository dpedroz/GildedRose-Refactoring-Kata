package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality() {
    for (item <- items) {
      ItemUpdateStrategy.forName(item.name).update(item)
    }
  }
}

object ItemUpdateStrategy {
  def forName(name:String): UpdateStrategy = name match {
    case "Aged Brie" => new CheeseStrategy()
    case "Sulfuras, Hand of Ragnaros" => new LegendaryStrategy()
    case "Backstage passes to a TAFKAL80ETC concert" => new BackstagePassStrategy()
    case "Conjured" => new ConjuredStrategy()
    case _ => new DefaultStrategy()
  }
}

class DefaultStrategy extends UpdateStrategy

class LegendaryStrategy extends UpdateStrategy {
  override def update(item: Item) = { }
}

class CheeseStrategy extends UpdateStrategy {
  override def update(item: Item) = {
    decreaseSellIn(item)
    increaseQuality(item)
  }
}

class BackstagePassStrategy extends UpdateStrategy {
  override def decreaseQuality(item: Item) = {
    item.quality = item.quality + (item.sellIn match {
      case s: Int if s >= 10 => 1
      case s: Int if s >= 5 => 2
      case s: Int if s >= 0 => 3
      case _ => -1 * item.quality
    })
    item.quality = math.min(50, item.quality)
  }
}

class ConjuredStrategy extends UpdateStrategy {
  override def decreaseQuality(item: Item) = item.quality = math.max(0, item.quality - (if (item.sellIn < 0) 4 else 2))
}

sealed trait UpdateStrategy {

  def update(item: Item): Unit = {
    decreaseSellIn(item)
    decreaseQuality(item)
  }

  protected def decreaseSellIn(item: Item) = item.sellIn = item.sellIn - 1
  protected def decreaseQuality(item: Item) = item.quality = math.max(0, item.quality - (if (item.sellIn < 0) 2 else 1))
  protected def increaseQuality(item: Item) = item.quality = math.min(50, item.quality + (if (item.sellIn < 0) 2 else 1))
}
