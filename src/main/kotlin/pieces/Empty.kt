package pieces

import Tile

object Empty : Piece {
    override val player = "no player"

    override fun availableMovementsFrom(currentTile: Tile): List<Tile> {
        return emptyList()
    }
}