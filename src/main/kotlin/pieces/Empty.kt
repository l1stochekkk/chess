package pieces

import Tile

object Empty : Piece {
    override val player = "no player"

    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        return emptyList()
    }
}