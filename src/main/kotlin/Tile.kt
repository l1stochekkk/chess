import pieces.*
import kotlin.math.abs

class Tile(val position: String) {

    override fun toString() = position

    var piece: Piece = Empty

    private fun pawnUpgrade(secondTile: Tile){
        val numberOfSecondTile = secondTile.position[1].digitToInt()
        if (piece is Pawn && numberOfSecondTile == 1 || numberOfSecondTile == 8) {
            val player = piece.player
            println("Choose new piece (type 'H', 'B', 'R' or 'Q')")
            while (true) {
                val inputText = readLine() ?: continue
                piece = when (inputText) {
                    "H" -> Horse(player)
                    "B" -> Bishop(player)
                    "R" -> Rook(player)
                    "Q" -> Queen(player)
                    else -> {
                        println("Wrong input")
                        continue
                    }
                }
                break
            }
        }
    }

    private fun swapEnPassant(secondTile: Tile){
        enPassant = if ((piece is Pawn) && abs(secondTile.position[1].digitToInt() - position[1].digitToInt()) == 2) {
            "${position[0]}" + "${(position[1].digitToInt() + secondTile.position[1].digitToInt()) / 2}"
        } else ""
    }

    private fun enPassantMove(secondTile: Tile){
        if (piece is Pawn && secondTile.isEmpty() && secondTile.position[0] != position[0]) {
            tileFromPosition(chessMap, secondTile.position[0], position[1]).piece = Empty
        }
    }

    private fun stalemateMove(secondTile: Tile){
        if (piece is King) {
            (piece as King).isPieceMoved = false
            if (abs(positionsFromTile(this).first - positionsFromTile(secondTile).first) == 2){
                val list = listOf(
                    Triple("a8", "c8", "c3"),
                    Triple("h8", "g8", "f8"),
                    Triple("a1", "c1", "c1"),
                    Triple("h1", "g1", "f1"))
                for (i in list){
                    val newKingPosition = i.second
                    if (newKingPosition == secondTile.position){
                        val oldRookPosition = i.first
                        val newRookPosition = i.third
                        tileFromPosition(chessMap, newRookPosition[0], newRookPosition[1]).piece = Rook(piece.player)
                        tileFromPosition(chessMap, oldRookPosition[0], oldRookPosition[1]).piece = Empty
                    }
                }
            }
        }
    }

    fun swapRookMovingState() = run { if (piece is Rook) (piece as Rook).isPieceMoved = true }

    fun isEmpty() = piece == Empty
    fun isNotEmpty() = piece != Empty

    fun movePieceTo(secondTile: Tile) {

        swapEnPassant(secondTile)
        enPassantMove(secondTile)
        pawnUpgrade(secondTile)
        stalemateMove(secondTile)
        swapRookMovingState()

        secondTile.piece = piece
        piece = Empty
    }
}

fun Pair<Int, Int>.inMapBounds() = first in (0..7) && second in (0..7)

fun positionsFromTile(tile: Tile): Pair<Int, Int> =
    ('a'..'h').indexOf(tile.position[0]) to 8 - tile.position[1].digitToInt()

fun tileFromPosition(map: List<List<Tile>>, letter: Char, number: Char): Tile =
    map[8 - number.digitToInt()][('a'..'h').indexOf(letter)]

fun getAvailableTileForPosition(x: Int, y: Int, player: String): Tile? {
    if (!(x to y).inMapBounds()) return null
    val possibleTile = chessMap[y][x]
    return if (possibleTile.isEmpty() || possibleTile.piece.player != player) {
        possibleTile
    } else null
}