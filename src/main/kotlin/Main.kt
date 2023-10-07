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
        //TODO make check for reset global state for pawns
        currentPlayer = swapCurrentPlayer(turn)
        chessMap.print(turn)
        while (true) {

            if (checkForMate(currentPlayer)) {
                println("Player $currentPlayer has lost!")
            }

            if (checkForCheck(currentPlayer)) println("$currentPlayer king is checked")

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

fun findKing(player: String) = chessMap.flatten().find { it.piece is King && it.piece.player == player }!!

fun allAvailableEnemyMovements(player: String) =
    chessMap.flatten().filter { it.piece != Empty && it.piece.player != player }.flatMap { it.piece.availableMovementsFrom(it) }.distinct()

fun checkForCheck(player: String) = findKing(player) in allAvailableEnemyMovements(player)

fun checkForMate(player: String): Boolean {
    val kingTile = findKing(player)
    return checkForCheck(player) && kingTile.piece.availableMovementsFrom(kingTile).isEmpty()
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