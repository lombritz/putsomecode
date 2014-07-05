package game.engine

import models.Tile
import scala.annotation.tailrec

/**
 * A hand represents a domino match.
 */
case class Hand(chain: List[Tile]) {
  require(validateChain, "The hand chain is invalid.")

  /**
   * Returns the side where the new newTile should be placed .
   */
  def whereToPlay(newTile: Tile): Option[Tile] =
    (chain.head, chain.last) match {
      case (head, _) if (head.left  == newTile.left || head.left  == newTile.right) => Some(head)
      case (_, last) if (last.right == newTile.left || last.right == newTile.right) => Some(last)
      case _ => None
    }

  /**
   * Plays a newTile on this hand.
   *
   * @return Returns the hand with the new newTile correctly placed.
   * @throws Error if newTile can't be placed in this match on any side.
   */
  def playTile(newTile: Tile): Hand = {
    val side = whereToPlay(newTile)
    side match {
      case Some(domino) => if (chain.head == domino) Hand(swapHeadIfNeeded(newTile) :: chain) else Hand(chain :+ swapLastIfNeeded(newTile))
      case _ => throw new Error("Can't play the selected newTile.")
    }
  }

  private def swapHeadIfNeeded(tile: Tile): Tile =
    if (chain.head.left == tile.right) tile
    else tile.swap

  private def swapLastIfNeeded(tile: Tile): Tile =
    if (chain.last.right == tile.left) tile
    else tile.swap

  /**
   * Validates that the tiles are correctly placed on this hands chain.
   *
   * @return Returns true if the hand chain is valid, otherwise returns false.
   */
  private def validateChain: Boolean = {
    @tailrec def _validateChain(c: List[Tile]): Boolean = c match {
      case x :: Nil => true
      case x :: y :: ys => if (x.right == y.left) _validateChain(y :: ys) else false
    }
    _validateChain(chain)
  }
}
