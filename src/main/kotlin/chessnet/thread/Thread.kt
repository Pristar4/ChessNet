package chessnet.thread

import chessnet.GenType
import chessnet.MoveList
import chessnet.Position
import chessnet.StateInfo
import chessnet.search.LimitsType
import chessnet.search.RootMove
import chessnet.search.RootMoves
import java.util.ArrayDeque


class Threads {
    companion object {
        fun startThinking(pos: Position, states: ArrayDeque<StateInfo>, limits: LimitsType, ponderMode: Boolean) {

            println("startThinking")



            val rootMoves: RootMoves = RootMoves()

            /*for (m in MoveList(pos, GenType.LEGAL)) {
                if (limits.searchMoves.isNotEmpty() && !limits.searchMoves.contains(m) || m ==null) continue
                    println(m)

            }*/
            for (m in MoveList(pos,GenType.QUIETS)) {
                if (limits.searchMoves.isNotEmpty() && !limits.searchMoves.contains(m) || m ==null) continue
                println(m)

            }




        }

    }
}
