package pieces

import Tile
import chessMap
import positionsFromTile

class Pawn(override val player: String) : Piece {

    private fun moveVerticalOne(x: Int, y: Int): Tile? {
        if (y in 1..6){
            if (player == "blue") {
                val possibleTile = chessMap[y - 1][x]
                if (possibleTile.isEmpty()) {
                    return possibleTile
                }
            } else if (player == "green") {
                val possibleTile = chessMap[y + 1][x]
                if (possibleTile.isEmpty()) {
                    return possibleTile
                }
            }
        }
        return null
    }

    private fun moveVerticalTwo(x: Int, y: Int): Tile? {
        if (y == 1 || y == 6){
            if (player == "blue") {
                val possibleTile = chessMap[y - 2][x]
                if (possibleTile.isEmpty()) {
                    return possibleTile
                }
            } else if (player == "green") {
                val possibleTile = chessMap[y + 2][x]
                if (possibleTile.isEmpty()) {
                    return possibleTile
                }
            }
        }
        return null
    }

    private fun moveDiagonalLeft(x: Int, y: Int): Tile? {
        if (0 <= x - 1) {
            if (player == "blue") {
                val possibleTile = chessMap[y - 1][x - 1]
                if (possibleTile.isNotEmpty() && possibleTile.piece.player != player) {
                    return possibleTile
                }
            }
            if (player == "green") {
                val possibleTile = chessMap[y + 1][x - 1]
                if (possibleTile.isNotEmpty() && possibleTile.piece.player != player) {
                    return possibleTile
                }
            }
        }
        return null
    }

    private fun moveDiagonalRight(x: Int, y: Int): Tile? {
        if (x + 1 <= 7) {
            if (player == "blue") {
                val possibleTile = chessMap[y - 1][x + 1]
                if (possibleTile.isNotEmpty() && possibleTile.piece.player != player) {
                    return possibleTile
                }
            }
            if (player == "green") {
                val possibleTile = chessMap[y + 1][x + 1]
                if (possibleTile.isNotEmpty() && possibleTile.piece.player != player) {
                    return possibleTile
                }
            }
        }
        return null
    }

    private fun availableMovementsFrom(currentTile: Tile): List<Tile> {
        val (x, y) = positionsFromTile(currentTile)
        return listOfNotNull(
            moveVerticalOne(x, y),
            moveVerticalTwo(x, y),
            moveDiagonalLeft(x, y),
            moveDiagonalRight(x, y)
            //TODO сделать взятие на проход
        )
    }

    override fun canMove(firstTile: Tile, secondTile: Tile): Boolean = secondTile in availableMovementsFrom(firstTile)
}
