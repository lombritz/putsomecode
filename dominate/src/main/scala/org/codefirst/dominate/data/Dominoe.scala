package org.codefirst.dominate.data


import Definitions._
import scala.util.Random

// Dominoe
case class Tile(left: Int, right: Int) {
  require(left >= 0 && left <= 6, "dominoe.left must be from 0 to 6.")
  require(right >= 0 && right <= 6, "dominoe.right must be from 0 to 6.")
  
  def sum = left + right
  
  def isDouble = left == right
  
  override def equals(o: Any) = {
    val d = o.asInstanceOf[Tile]
    (left == d.left && right == d.right) ||
    (left == d.right && right == d.left)
  }
}

object Definitions {
  type DominoeSet = List[Tile]
}

// Players and Moves
case class Player(name: String, hand: DominoeSet) {
  def has(tile: Tile) = hand contains tile
}
case class Move(tile: Tile, table: DominoeSet)

class GameDef(winningPoints: Int, playerNames: List[String]) {
  
  val dominoes = List(
    Tile(0,0), Tile(0,1), Tile(0,2), Tile(0,3), Tile(0,4), Tile(0,5), Tile(0,6),
    Tile(1,1), Tile(1,2), Tile(1,3), Tile(1,4), Tile(1,5), Tile(1,6),
    Tile(2,2), Tile(2,3), Tile(2,4), Tile(2,5), Tile(2,6),
    Tile(3,3), Tile(3,4), Tile(3,5), Tile(3,6),
    Tile(4,4), Tile(4,5), Tile(4,6),
    Tile(5,5), Tile(5,6),
    Tile(6,6)
  )
  
  
  
  def players: List[Player] = {
    def iter(namesLeft: List[String], pool: List[Tile]): List[Player] = namesLeft match {
      case name :: xs => Player(name, pool.take(7)) :: iter(xs, pool.drop(7))
      case _ => List()
    }
    iter(playerNames, Random.shuffle(dominoes))
  }
  
}
object GameDef {
  def apply(winningPoints: Int, playerNames: List[String]) = new GameDef(winningPoints, playerNames)
}

