import chessnet.Board
import chessnet.engineInfo
import chessnet.uci.Uci

fun main(args: Array<String>) {
    val argc = args.size
    println(engineInfo())
    // Initialize UCI with options
    Uci.init(args)
    // Create Board
    Board.init()

    Board.pretty()

    Uci.loop(argc, args)
}
