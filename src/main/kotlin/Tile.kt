import pieces.*

class Tile(val position: String) {

    override fun toString() = position

    var piece: Piece = Empty

    fun isEmpty() = piece == Empty

    fun isNotEmpty() = piece != Empty

    fun movePieceTo(secondTile: Tile) {
        secondTile.piece = piece
        piece = Empty
    }

}

fun Pair<Int, Int>.inMapBounds() = first in (0..7) && second in (0..7)

fun positionsFromTile(tile: Tile): Pair<Int, Int> =
    ('a'..'h').indexOf(tile.position[0]) to 8 - tile.position[1].digitToInt()

fun tileFromPosition(letter: Char, number: Char): Tile = chessMap[8 - number.digitToInt()][('a'..'h').indexOf(letter)]

fun getAvailableTileForPosition(x: Int, y: Int, player: String): Tile? {
    if (!(x to y).inMapBounds()) return null
    val possibleTile = chessMap[y][x]
    return if (possibleTile.isEmpty() || possibleTile.piece.player != player) {
        possibleTile
    } else null
}
