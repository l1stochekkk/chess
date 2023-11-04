import pieces.*

val chessMap: List<List<Tile>> = (8 downTo 1).map { num ->
    ('a'..'h').map { char ->
        Tile("$char$num").apply {
            piece = pieceTemplate.getOrDefault("$char$num", Empty)
        }
    }
}
var enPassant = ""

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

            if (checkForMate(chessMap, currentPlayer)) {
                println("Player $currentPlayer has lost!")
                break
            }

            if (checkForStalemate(chessMap, currentPlayer)) {
                println("Stalemate! Draw!")
                break
            }

            if (checkForCheck(chessMap, currentPlayer)) println("$currentPlayer king is checked")

            val inputText = readLine() ?: continue

            val currentTile = parseMove(chessMap, inputText)?.first ?: continue
            val secondTile = parseMove(chessMap, inputText)?.second ?: continue

            if (currentTile.piece.player != currentPlayer) {
                println("You don`t have a piece on $currentTile")
                continue
            }

            if (!currentTile.piece.canMove(chessMap, currentTile, secondTile)) {
                println("This ${currentTile.piece.name} can`t move from ${currentTile.position} on $secondTile")
                continue
            }

            currentTile.movePieceTo(secondTile)

            if (checkForCheck(chessMap, currentPlayer)) {
                println("Your king will be under attack")
                secondTile.movePieceTo(currentTile)
                continue
            }

            break
        }
    }
}

fun findKing(map: List<List<Tile>>, player: String) = map.flatten().find { it.piece is King && it.piece.player == player }!!

fun allAvailablePlayersMovements(map: List<List<Tile>>,player: String) =
    map.flatten().filter { it.piece.player == player }.flatMap { it.piece.availableMovementsFrom(map, it) }.distinct()

fun allAvailableEnemyMovements(map: List<List<Tile>>, player: String) =
    map.flatten().filter { it.piece != Empty && it.piece.player != player }.flatMap { it.piece.availableMovementsFrom(map, it) }.distinct()

fun allAvailableEnemyMovementsKing(map: List<List<Tile>>,player: String) =
    map.flatten().filter { it.piece != Empty && it.piece.player != player }.flatMap { it.piece.availableMovementsFrom(map, it) }.distinct()

fun checkForCheck(map: List<List<Tile>>, player: String) = findKing(map, player) in allAvailableEnemyMovements(map, player)

fun checkForMate(map: List<List<Tile>>, player: String): Boolean {
    val kingTile = findKing(chessMap, player)
    return checkForCheck(map, player) && kingTile.piece.availableMovementsFrom(map, kingTile).isEmpty()
}

fun checkForStalemate(map: List<List<Tile>>, player: String) = allAvailablePlayersMovements(map, player).isEmpty()


fun swapCurrentPlayer(turn: Int): String {
    return if (turn % 2 == 0) "green" else "blue"
}

fun parseMove(map: List<List<Tile>>, inputText: String): Pair<Tile, Tile>? {
    if (!"^[a-h][1-8][a-h][1-8]\$".toRegex().matches(inputText)) {
        println("Incorrect input")
        return null
    }
    return Pair(tileFromPosition(map, inputText[0], inputText[1]), tileFromPosition(map, inputText[2], inputText[3]))
}