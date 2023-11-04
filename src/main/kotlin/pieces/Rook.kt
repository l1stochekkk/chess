package pieces

import Tile

class Rook(override val player: String) : Piece {

    var isPieceMoved = false
    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val signs = listOf(0 to 1, 0 to -1, -1 to 0, 1 to 0)
        val posTileList = mutableListOf<Tile>()
        signs.forEach { calculateMove(currentTile, it.first, it.second, posTileList) }

        return posTileList
    }
}