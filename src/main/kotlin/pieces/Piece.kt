package pieces

import Tile
import checkForCheck
import getAvailableTileForPosition
import positionsFromTile

interface Piece {

    val player: String

    val name get() = this::class.simpleName

    val symbol
        get() = when (this) {
            is Rook -> "R"
            is Horse -> "H"
            is Bishop -> "B"
            is Pawn -> "P"
            is Queen -> "Q"
            is King -> "K"
            else -> "x"
        }

    fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile>

    fun calculateMove(currentTile: Tile, xSign: Int, ySign: Int, posTileList: MutableList<Tile>){
        val (x, y) = positionsFromTile(currentTile)
        for (i in 1..7) {
            val possibleTile = getAvailableTileForPosition(x + i * xSign, y + i * ySign, player) ?: break
            posTileList.add(possibleTile)
            if (possibleTile.isNotEmpty()) break
        }
    }

    fun canMove(map: List<List<Tile>>, firstTile: Tile, secondTile: Tile): Boolean =
        secondTile in availableMovementsFrom(map, firstTile) && !checkForCheck(map, player)
}