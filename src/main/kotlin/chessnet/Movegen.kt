package chessnet

import java.util.Vector
import chessnet.GenType.*

enum class GenType {
    CAPTURES, QUIETS, QUIET_CHECKS, EVASIONS, NON_EVASIONS, LEGAL,
}


class ExtMove {
    var moveList: Vector<Move> = Vector()
    var move: Move = Move()
    var value: Int = 0


    fun Move(): Move {
        moveList.add(move)
        return move

    }

    fun clear() {
        moveList.clear()
    }


}

class Movegen {


    companion object {
        fun generate(pos: Position, moveList: ExtMove, type: GenType): ExtMove? {
            assert((type == EVASIONS) == (pos.checkers() != 0UL))

            return null

        }

    }
}

