import chessnet.engineInfo
import chessnet.uci.Uci

fun main(argv: Array<String>) {
    println(engineInfo())

    Uci.loop(argv)
}

