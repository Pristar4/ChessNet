import chessnet.*

fun main(argv: Array<String>) {


    println(engineInfo())
    println()

    Bitboards.init()
    print( Bitboards.pretty(0x0102040810204080uL))



    Uci.loop(argv)

//    val board: Board = Board();
//    board.initWhite();
//    board.initBlack();
//    println(board);
}

