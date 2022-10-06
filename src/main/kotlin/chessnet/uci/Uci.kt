package chessnet.uci

import chessnet.position.Position

object Uci {

    const val StartFEN =
        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

    fun init(options: Any) {
        println("uciok")
        Position.set(StartFEN)
        return
    }

    fun loop(argc: Int, argv: Array<String>) {
        // TODO: implement Uci.loop()
        return
    }

    fun position(fen: String) {
        Position.set(fen)
        return
    }
}
