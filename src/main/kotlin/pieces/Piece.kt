package pieces

import Tile

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

    fun canMove(firstTile: Tile, secondTile: Tile): Boolean
}