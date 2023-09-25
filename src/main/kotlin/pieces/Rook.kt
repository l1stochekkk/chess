package pieces

import Tile
import getAvailableTileForPosition
import positionsFromTile

class Rook(override val player: String) : Piece {

    override fun availableMovementsFrom(currentTile: Tile): List<Tile> {
        val (x, y) = positionsFromTile(currentTile)
        val posTileList = mutableListOf<Tile>()
        for (i in -7..7) {
            getAvailableTileForPosition(x, y + i, player)?.let { posTileList.add(it) }
            getAvailableTileForPosition(x + i, y, player)?.let { posTileList.add(it) }
        }
        return posTileList
    }
}