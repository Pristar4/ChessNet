import chessnet.*

fun main(argv: Array<String>) {


    println(engineInfo())

    Bitboards.init()


    Uci.loop(argv)

//    val board: Board = Board();
//    board.initWhite();
//    board.initBlack();
//    println(board);
}

