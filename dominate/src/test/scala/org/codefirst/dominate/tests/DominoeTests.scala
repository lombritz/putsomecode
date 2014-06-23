package org.codefirst.dominate.tests

import org.scalatest.FlatSpec
import org.codefirst.dominate.data.Tile

class DominoeTests extends FlatSpec {
  
  "The sum of a tile" should "be the sum of the left and right numbers that compose it" in {
    assert(Tile(5, 4).sum == 9)
  }
  
  "The game "
}