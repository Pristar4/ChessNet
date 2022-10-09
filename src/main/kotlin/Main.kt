import chessnet.*

fun main(argv: Array<String>) {


    println(engineInfo())
    println()

    Bitboards.init()


    Uci.loop(argv)

//    val board: Board = Board();
//    board.initWhite();
//    board.initBlack();
//    println(board);
}

