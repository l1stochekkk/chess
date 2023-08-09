package pieces

import Tile
import getAvailableTileForPosition
import positionsFromTile

class Horse(override val player: String) : Piece {

    private fun availableMovementsFrom(currentTile: Tile): List<Tile> {
        val (x, y) = positionsFromTile(currentTile)
        val posTileList = mutableListOf<Tile>()
        for (i in -1..1 step 2) {
            for (n in -2..2 step 4) {
                getAvailableTileForPosition(x + i, y + n, player)?.let { posTileList.add(it) }
                getAvailableTileForPosition(x + n, y + i, player)?.let { posTileList.add(it) }
            }
        }
        return posTileList
    }

    override fun canMove(firstTile: Tile, secondTile: Tile): Boolean = secondTile in availableMovementsFrom(firstTile)

}