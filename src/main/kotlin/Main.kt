import chessnet.engineInfo
import chessnet.uci.Uci
import chessnet.Board

fun main(args: Array<String>) {
    val argc = args.size
    println(engineInfo())


    // Initialize UCI with options
    Uci.init("")

    // Create Board
    Board.init()




    Uci.loop(argc, args)


}




