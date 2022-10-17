package chessnet.search

import chessnet.Move
import chessnet.Position
import chessnet.Value.*
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
