package pieces

import Tile
import allAvailableEnemyMovementsKing
import getAvailableTileForPosition
import positionsFromTile

class King(override val player: String) : Piece {

    override fun calculateMove(currentTile: Tile, xSign: Int, ySign: Int, posTileList: MutableList<Tile>){
        val (x, y) = positionsFromTile(currentTile)
        for (i in -1..1 step 2) {
            val possibleTile = getAvailableTileForPosition(x + i * xSign, y + i * ySign, player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
            if (possibleTile.isNotEmpty()) break
        }
    }
    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val signs = listOf(1 to 1, 1 to -1, -1 to 1, -1 to -1, 0 to 1, 0 to -1, -1 to 0, 1 to 0)
        val posTileList = mutableListOf<Tile>()
        signs.forEach { calculateMove(currentTile, it.first, it.second, posTileList) }

        return posTileList
    }

    override fun canMove(map: List<List<Tile>>, firstTile: Tile, secondTile: Tile): Boolean =
        secondTile in availableMovementsFrom(map, firstTile).filter { it !in allAvailableEnemyMovementsKing(map,player) }

}