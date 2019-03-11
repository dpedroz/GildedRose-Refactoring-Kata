package com.gildedrose

class GildedRose(val items: Array[Item]) {

  def updateQuality() {
    for (item <- items) { item.updateQuality }
  }
}
