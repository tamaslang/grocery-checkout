package org.talangsoft.grocery

case class Product(name: String, price: BigDecimal) {
  def *(n: Int) = Seq.fill(n)(this)

  def +(product: Product) = Seq(this, product)

  def +(products: Seq[Product]) = Seq(this) ++ products
}

object ProductRepo {
  val apple = Product("Apple", 0.25)
  val orange = Product("Orange", 0.6)
}

object Model {
  final type Basket = Seq[Product]
  final type ProductDiscount = Basket => BigDecimal
}

import Model._

object Discounts {
  val twoForOneAppleDiscount: ProductDiscount = products =>
    BigDecimal(products.count(p => p == ProductRepo.apple) / 2) * ProductRepo.apple.price
  val threeForTwoOrangeDiscount: ProductDiscount = products =>
    BigDecimal(products.count(p => p == ProductRepo.orange) / 3) * ProductRepo.orange.price
}

trait Shop {
  val productDiscounts: Seq[ProductDiscount]
}

object Shop extends Shop {
  override val productDiscounts = Seq(Discounts.twoForOneAppleDiscount, Discounts.threeForTwoOrangeDiscount)

  def checkout(basket: Basket): BigDecimal =
    basket.map(_.price).sum - productDiscounts.map(_ (basket)).sum
}

