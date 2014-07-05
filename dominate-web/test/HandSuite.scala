import game.engine.Hand
import models.Tile
import org.scalatest.FunSuite

class HandSuite extends FunSuite {
  val hand = Hand(List(Tile(0,4), Tile(4,6), Tile(6,6), Tile(6,5), Tile(5,1)))

  test("I should be able to play a newTile and it should automatically be placed on the right side") {
    val newHand = hand.playTile(Tile(5,0))
    val expectedHand = Hand(Tile(5,0) :: hand.chain)
    println(newHand)
    assert(newHand == expectedHand)
    val newHand2 = hand.playTile(Tile(2,1))
    val expectedHand2 = Hand(hand.chain :+ Tile(1,2))
    println(newHand2)
    assert(newHand2 == expectedHand2)
  }

  test("An Error should be thrown if someone tries to play a not allowed newTile") {
    intercept[Error] {
      hand.playTile(Tile(3,4))
    }
  }


}