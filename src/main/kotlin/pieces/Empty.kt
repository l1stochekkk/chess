package pieces

import Tile

object Empty : Piece {
    override val player = "no player"
    override fun canMove(firstTile: Tile, secondTile: Tile): Boolean {
        TODO("Not yet implemented")
    }
}