package chessnet.search

import chessnet.Color
import chessnet.Move
import chessnet.Position
import chessnet.Uci
import chessnet.Value.*
import chessnet.thread.Thread
import chessnet.thread.Thread.Companion.rootPos
import java.util.*

typealias RootMoves = Vector<RootMove>

class RootMove(val move: Move) {
    var score: Int = -VALUE_INFINITE
    var previousScore: Int = -VALUE_INFINITE
    var averageScore: Int = -VALUE_INFINITE
    var selDepth: Int = 0
    var tbRank: Int = 0
    var tbScore: Int = 0
    var pv: Vector<Move> = Vector()

    init {
        pv.add(move)
    }

    fun extractPonderFromTT(pos: Position): Boolean {
        return false
    }

    fun operatorEquals(m: Move): Boolean {
        return pv.elementAt(0) == m
    }

    fun operatorLess(m: RootMove): Boolean {
        return if (m.score != score) {
            m.score < score
        } else {
            m.previousScore < previousScore
        }
    }
}

/**
 * clear() resets search state to its initial value.
 */
fun clear(){
    // TODO()
}
fun search() {

    var us:Color = rootPos.sideToMove

    //choose random move from rootMoves

    var bestMove: Move = Thread.rootMoves.elementAt(Random().nextInt(Thread.rootMoves.size)).move

    //print best move
    println("bestmove ${Uci.move(bestMove, rootPos.isChess960)}")

    return




}
