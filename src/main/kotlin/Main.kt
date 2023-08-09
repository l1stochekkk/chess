import pieces.*

val chessMap: List<List<Tile>> = (8 downTo 1).map { num ->
    ('a'..'h').map { char ->
        Tile("$char$num").apply {
            piece = pieceTemplate.getOrDefault("$char$num", Empty)
        }
    }
}

fun main() {
    println("Welcome to chess!")
    println("Start game?   y/n")
    if (readLine() != "y") return
    var currentPlayer: String
    for (turn in 1..Int.MAX_VALUE) {
        currentPlayer = swapCurrentPlayer(turn)
        chessMap.print(turn)
        while (true) {
            val inputText = readLine() ?: continue

            val currentTile = parseMove(inputText)?.first ?: continue
            val secondTile = parseMove(inputText)?.second ?: continue

            if (currentTile.piece.player != currentPlayer) {
                println("You don`t have a piece on $currentTile")
                continue
            }

            if (!currentTile.piece.canMove(currentTile, secondTile)) {
                println("This ${currentTile.piece.name} can`t move from ${currentTile.position} on $secondTile")
                continue
            }

            currentTile.movePieceTo(secondTile)
            break
        }
    }
}

fun swapCurrentPlayer(turn: Int): String {
    return if (turn % 2 == 0) "green" else "blue"
}

fun parseMove(inputText: String): Pair<Tile, Tile>? {
    if (!"^[a-h][1-8][a-h][1-8]\$".toRegex().matches(inputText)) {
        println("Incorrect input")
        return null
    }
    return Pair(tileFromPosition(inputText[0], inputText[1]), tileFromPosition(inputText[2], inputText[3]))
}