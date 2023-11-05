package pieces

import Tile
import allAvailableEnemyMovements
import allAvailableEnemyMovementsKing
import chessMap
import getAvailableTileForPosition
import positionsFromTile
import tileFromPosition
import kotlin.math.abs

class King(override val player: String) : Piece {

    var isPieceMoved = false

    override fun calculateMove(currentTile: Tile, xSign: Int, ySign: Int, posTileList: MutableList<Tile>) {
        val (x, y) = positionsFromTile(currentTile)
        val possibleTile = getAvailableTileForPosition(x + xSign, y + ySign, player) ?: return

        //0, 0 - up/left
        //0, 7 - up/right
        //7, 0 - down/left
        //7, 7 - down/right

        if (abs(xSign) == 2 && !isPieceMoved) {
            listOf(
                Triple(0, 0, "c8"),
                Triple(0, 7, "g8"),
                Triple(7, 0, "c1"),
                Triple(7, 7, "g1")
            ).forEach {
                val number = it.first
                val letter = it.second
                val newKingPosition = it.third
                val rook = chessMap[number][letter].piece
                if (possibleTile.position == newKingPosition && rook is Rook) {
                    if (!rook.isPieceMoved) {
                        val castlingSpace = when (newKingPosition) {
                            "c8" -> listOf("e8", "d8", "c8", "b8", "a8")
                            "h8" -> listOf("e8", "f8", "g8", "h8")
                            "c1" -> listOf("e1", "d1", "c1", "b1", "a1")
                            "g1" -> listOf("e1", "f1", "g1", "h1")
                            else -> return
                        }
                        val checkForSpaceIsUnderAttack = {
                            castlingSpace.filter { tile ->
                                tileFromPosition(
                                    chessMap, tile[0], tile[1]
                                ) !in allAvailableEnemyMovements(chessMap, player)
                            } == castlingSpace
                        }
                        val checkSpaceBetweenPieces = {
                            val castlingSpaceSublisted = castlingSpace.subList(1, castlingSpace.size - 1)
                            castlingSpaceSublisted.filter { tile -> tile.isNotEmpty() } == castlingSpaceSublisted
                        }
                        if (!checkForSpaceIsUnderAttack.invoke() && !checkSpaceBetweenPieces.invoke()) {
                            return
                        }
                    }
                }
            }
        }
        posTileList.add(possibleTile)
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