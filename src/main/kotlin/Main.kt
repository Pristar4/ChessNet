import chessnet.*

fun main(argv: Array<String>) {


    println(engineInfo())
    println()

    Bitboards.init()
    println(Bitboards.pretty(PseudoAttacks[PieceType.KING.value][26]))


    Uci.loop(argv)

//    val board: Board = Board();
//    board.initWhite();
//    board.initBlack();
//    println(board);
}

