package game.engine

/**
 * Created by jaime on 6/26/14.
 */
case class Tile(left: Int, right: Int) {

  def sum = left + right

  def swap = Tile(right, left)

  override def equals(o: Any) = {
    val t = o.asInstanceOf[Tile]
    (left == t.left && right == t.right) || (left == t.right &&
      right == t.left)
  }

  override def toString = "[" + left + "|" + right + "]"
}
