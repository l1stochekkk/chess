package pieces

import Tile
import getAvailableTileForPosition
import positionsFromTile

class Bishop(override val player: String) : Piece {

    override fun availableMovementsFrom(currentTile: Tile): List<Tile> {
        val (x, y) = positionsFromTile(currentTile)
        val posTileList = mutableListOf<Tile>()
        for (i in 1..7) {
            val possibleTile = getAvailableTileForPosition(x + i, y + i , player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
        }
        for (i in 1..7) {
            val possibleTile = getAvailableTileForPosition(x + i, y - i , player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
        }
        for (i in 1..7) {
            val possibleTile = getAvailableTileForPosition(x - i, y + i , player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
        }
        for (i in 1..7) {
            val possibleTile = getAvailableTileForPosition(x - i, y - i , player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
        }

        for (i in 1..7){
            for (k in 1..7){
                val possibleTile = getAvailableTileForPosition(x + i, y + i , player) ?: break
                posTileList.add(possibleTile)
                if (possibleTile.isNotEmpty()) break
            }
        }

        return posTileList
    }
}