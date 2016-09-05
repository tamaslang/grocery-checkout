package org.talangsoft.grocery

import org.scalatest.prop.{TableDrivenPropertyChecks, Tables}
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
  * Apples and oranges task:
  * Products:
  * Apples = 0.6 / piece
  * Oranges = 0.25 / piece
  * Discounts:
  * 3 orange for the price of 2
  * 2 apples for 1
  */
class ApplesAndOrangesCheckoutSpec extends FlatSpec with ShouldMatchers {
  val scenarios = Tables.Table[String, Seq[Product], Double](
    ("scenario", "", "expectedPrice"),
    ("Empty basket", Seq(), 0),
    ("1 apple", ProductRepo.apple * 1, 0.25),
    ("2 apples", ProductRepo.apple * 1, 0.25),
    ("3 apples", ProductRepo.apple * 3, 0.5),
    ("4 apples", ProductRepo.apple * 4, 0.5),
    ("5 apples", ProductRepo.apple * 5, 0.75),
    ("6 apples", ProductRepo.apple * 5, 0.75),
    ("1 orange", ProductRepo.orange * 1, 0.6),
    ("2 oranges", ProductRepo.orange * 2, 1.2),
    ("3 oranges", ProductRepo.orange * 3, 1.2),
    ("4 oranges", ProductRepo.orange * 4, 1.8),
    ("5 oranges", ProductRepo.orange * 5, 2.4),
    ("6 oranges", ProductRepo.orange * 6, 2.4),
    ("7 oranges", ProductRepo.orange * 7, 3.0),
    ("1 apple and 1 orange", ProductRepo.apple + ProductRepo.orange, 0.85),
    ("1 apple and 2 oranges", ProductRepo.apple + ProductRepo.orange * 2, 1.45),
    ("2 apple and 2 oranges", (ProductRepo.apple * 2) ++ (ProductRepo.orange * 2), 1.45),
    ("2 apple and 3 oranges", (ProductRepo.apple * 2) ++ (ProductRepo.orange * 3), 1.45)
  )

  TableDrivenPropertyChecks.forAll(scenarios) { (scenario, basket, expectedPrice) =>
    s"$scenario" should s"cost $expectedPrice on checkout" in new Setup {
      underTest.checkout(basket) shouldBe expectedPrice
    }
  }

  trait Setup {
    val underTest = Shop
  }

}


