import chessnet.*

fun main(argv: Array<String>) {


    println(engineInfo())

    Bitboards.init()


    Uci.loop(argv)
}

