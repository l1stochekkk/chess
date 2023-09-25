package pieces

import Tile
import getAvailableTileForPawnPosition
import positionsFromTile

class Pawn(override val player: String) : Piece {
        override fun availableMovementsFrom(currentTile: Tile): List<Tile> {
        val (x, y) = positionsFromTile(currentTile)
        val posTileList = mutableListOf<Tile>()
        if (currentTile.piece.player == "green"){
            //for green
            getAvailableTileForPawnPosition(x to y, x, y + 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(x to y, x, y + 2, player)?.let { posTileList.add(it) }
            //for green - attack
            getAvailableTileForPawnPosition(x to y, x + 1, y + 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(x to y, x - 1, y + 1, player)?.let { posTileList.add(it) }
        }
        if (currentTile.piece.player == "blue") {
            //for blue
            getAvailableTileForPawnPosition(x to y, x, y - 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(x to y, x, y - 2, player)?.let { posTileList.add(it) }
            //for blue - attack
            getAvailableTileForPawnPosition(x to y, x + 1, y - 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(x to y, x - 1, y - 1, player)?.let { posTileList.add(it) }
        }
        return posTileList
    }
}
