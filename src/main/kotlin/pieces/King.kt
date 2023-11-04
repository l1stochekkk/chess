package pieces

import Tile
import allAvailableEnemyMovementsKing
import chessMap
import getAvailableTileForPosition
import positionsFromTile
import kotlin.math.abs

class King(override val player: String) : Piece {

    var isPieceMoved = false

    override fun calculateMove(currentTile: Tile, xSign: Int, ySign: Int, posTileList: MutableList<Tile>) {
        val (x, y) = positionsFromTile(currentTile)
        val possibleTile = getAvailableTileForPosition(x + xSign, y + ySign, player) ?: return
        //val horizontalLine: (Tile) -> Boolean = { tile -> tile.piece is Rook }

        //0, 0 - up/left
        //0, 7 - up/right
        //7, 0 - down/left
        //7, 7 - down/right

        if (abs(xSign) == 2 && isPieceMoved == false) {
            val rook = chessMap[0][0].piece
            if (rook is Rook && !rook.isPieceMoved)
                posTileList.add(possibleTile)
            } else posTileList.add(possibleTile)
    }

    override fun availableMovementsFrom(map: List<List<Tile>>, currentTile: Tile): List<Tile> {
        val signs = listOf(1 to 1, 1 to -1, -1 to 1, -1 to -1, 0 to 1, 0 to -1, -1 to 0, 1 to 0, 2 to 0, -2 to 0)
        val posTileList = mutableListOf<Tile>()
        signs.forEach { calculateMove(currentTile, it.first, it.second, posTileList) }

        return posTileList
    }

    override fun canMove(map: List<List<Tile>>, firstTile: Tile, secondTile: Tile): Boolean =
        secondTile in availableMovementsFrom(map, firstTile).filter {
            it !in allAvailableEnemyMovementsKing(map, player)
        }

}