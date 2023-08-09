import pieces.*

fun List<List<Tile>>.print(turn: Int) {
    println("Turn $turn, ${if (turn % 2 == 0) "green" else "blue"}")
    this.forEachIndexed { index, tileList ->
        print(tileList.joinToString("  ", "", "  |${8 - index}") {
            coloringTemplate.getOrDefault(it.piece.player, originalColor) + it.piece.symbol + originalColor
        })
        println()
    }
    println("-  -  -  -  -  -  -  -")
    println("A  B  C  D  E  F  G  H")
}

val coloringTemplate = mapOf(
    "blue" to "\u001b[34m",
    "green" to "\u001b[32m",
)

const val originalColor = "\u001b[0m"

val pieceTemplate = mapOf(
    "a1" to Rook("blue"), "b1" to Horse("blue"), "c1" to Bishop("blue"), "d1" to Queen("blue"),
    "e1" to King("blue"), "f1" to Bishop("blue"), "g1" to Horse("blue"), "h1" to Rook("blue"),
    "a2" to Pawn("blue"), "b2" to Pawn("blue"), "c2" to Pawn("blue"), "d2" to Pawn("blue"),
    "e2" to Pawn("blue"), "f2" to Pawn("blue"), "g2" to Pawn("blue"), "h2" to Pawn("blue"),
    "a8" to Rook("green"), "b8" to Horse("green"), "c8" to Bishop("green"), "d8" to Queen("green"),
    "e8" to King("green"), "f8" to Bishop("green"), "g8" to Horse("green"), "h8" to Rook("green"),
    "a7" to Pawn("green"), "b7" to Pawn("green"), "c7" to Pawn("green"), "d7" to Pawn("green"),
    "e7" to Pawn("green"), "f7" to Pawn("green"), "g7" to Pawn("green"), "h7" to Pawn("green"),
)