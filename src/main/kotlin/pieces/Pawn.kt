package pieces

import Tile
import enPassant
import getAvailableTileForPosition
import positionsFromTile

class Pawn(override val player: String) : Piece {
    override fun calculateMove(currentTile: Tile, xSign: Int, ySign: Int, posTileList: MutableList<Tile>) {
        val (x, y) = positionsFromTile(currentTile)
        val playerMod = when (player) {
            "blue" -> -1
            "green" -> 1
            else -> 0
        }
        val possibleTile = getAvailableTileForPosition(x + (xSign * playerMod), y + (ySign * playerMod), player)
        if (possibleTile != null) {
            if (xSign != 0 && possibleTile.isNotEmpty() && possibleTile.piece.player != player) posTileList.add(
                possibleTile
            )
            if (ySign == 2 && (positionsFromTile(currentTile).second == 1 || positionsFromTile(currentTile).second == 6)
                && possibleTile.isEmpty()
            ) {
                posTileList.add(possibleTile)
            }
            if ((ySign == 1 && xSign == 0) && possibleTile.isEmpty()) posTileList.add(possibleTile)

            if (xSign != 0 && possibleTile.isEmpty() && possibleTile.position == enPassant) posTileList.add(possibleTile)
        }
    }

    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val signs = listOf(0 to 1, 0 to 2, 1 to 1, -1 to 1)
        val posTileList = mutableListOf<Tile>()
        signs.forEach { calculateMove(currentTile, it.first, it.second, posTileList) }
        return posTileList
    }
}