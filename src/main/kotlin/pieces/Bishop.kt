package pieces

import Tile

class Bishop(override val player: String) : Piece {

    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val signs = listOf(1 to 1, 1 to -1, -1 to 1, -1 to -1)
        val posTileList = mutableListOf<Tile>()
        signs.forEach { calculateMove(currentTile, it.first, it.second, posTileList) }

        return posTileList
    }
}