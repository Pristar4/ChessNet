package chessnet.thread

import chessnet.*
import chessnet.search.LimitsType
import chessnet.search.RootMove
import chessnet.search.RootMoves
import chessnet.search.search
import java.util.ArrayDeque


class Thread {
    companion object {
        var rootPos: Position = Position()
        var rootState: StateInfo = StateInfo()
            val rootMoves: RootMoves = RootMoves()
        fun startThinking(pos: Position, states: ArrayDeque<StateInfo>, limits: LimitsType, ponderMode: Boolean) {

            rootPos.set( pos.fen(),pos.isChess960,rootState)

            for (m in MoveList(pos, GenType.LEGAL)) {
                if (limits.searchMoves.isNotEmpty() && !limits.searchMoves.contains(m) || m ==null) continue
                rootMoves.add(RootMove(m))
            }
            search()
            //


        }
    }
}
