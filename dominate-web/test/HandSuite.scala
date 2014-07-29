import game.engine.{Tile, Hand}
import org.scalatest.FunSuite

class HandSuite extends FunSuite {
  val hand = Hand(List(Tile(0,4), Tile(4,6), Tile(6,6), Tile(6,5), Tile(5,1)))

  test("I should be able to play a tile and it should automatically be placed on the correct place") {
    val newHand = hand.playTile(Tile(5,0))
    val expectedHand = Hand(Tile(5,0) :: hand.chain)
    assert(newHand == expectedHand)

    val newHand2 = hand.playTile(Tile(2,1))
    val expectedHand2 = Hand(hand.chain :+ Tile(1,2))
    assert(newHand2 == expectedHand2)
  }

  test("I should catch an error if someone tries to play a not allowed tile") {
    intercept[Error] {
      hand.playTile(Tile(2,4))
    }
  }

  test("I should be able to get the sum of all tiles points placed on the hand") {
    assert(hand.sum == 43)
  }

}