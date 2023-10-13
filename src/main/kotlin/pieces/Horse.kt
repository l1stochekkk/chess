package pieces

import Tile
import getAvailableTileForPosition
import positionsFromTile

class Horse(override val player: String) : Piece {

    override fun calculateMove(currentTile: Tile, xSign: Int, ySign: Int, posTileList: MutableList<Tile>){
        val (x, y) = positionsFromTile(currentTile)
        for (i in 1..2) {
            val possibleTile = getAvailableTileForPosition(x + i * xSign, y + (3 - i)  * ySign, player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
        }
    }

    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val signs = listOf(1 to 1, 1 to -1, -1 to 1, -1 to -1)
        val posTileList = mutableListOf<Tile>()
        signs.forEach { calculateMove(currentTile, it.first, it.second, posTileList) }

        return posTileList
    }
}