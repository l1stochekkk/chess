package pieces

import Tile
import getAvailableTileForPawnPosition
import positionsFromTile

class Pawn(override val player: String) : Piece {
        override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val (x, y) = positionsFromTile(currentTile)
        val posTileList = mutableListOf<Tile>()
        if (currentTile.piece.player == "green"){
            //for green
            getAvailableTileForPawnPosition(map, x to y, x, y + 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(map,x to y, x, y + 2, player)?.let { posTileList.add(it) }
            //for green - attack
            getAvailableTileForPawnPosition(map, x to y, x + 1, y + 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(map, x to y, x - 1, y + 1, player)?.let { posTileList.add(it) }
        }
        if (currentTile.piece.player == "blue") {
            //for blue
            getAvailableTileForPawnPosition(map, x to y, x, y - 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(map, x to y, x, y - 2, player)?.let { posTileList.add(it) }
            //for blue - attack
            getAvailableTileForPawnPosition(map, x to y, x + 1, y - 1, player)?.let { posTileList.add(it) }
            getAvailableTileForPawnPosition(map, x to y, x - 1, y - 1, player)?.let { posTileList.add(it) }
        }
        return posTileList
    }
}
