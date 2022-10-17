package chessnet

import java.util.Vector
import chessnet.GenType.*
import chessnet.Movegen.Companion.generate
import java.util.BitSet

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

/** The MoveList struct is a simple wrapper around generate(). It sometimes comes
 * in handy to use this class instead of the low level generate() function.
 */
class MoveList(val pos: Position, val type: GenType) {
    var cur: Int = 0
    var end: Int = 0
    var moves: ExtMove = ExtMove()


    private val moveList: ExtMove = ExtMove()

    init {
        moves = generate(pos, moveList, type)
        println("moves: ${moves.moveList}")
        cur = 0
        end = moveList.moveList.size

    }

    fun size(): Int {
        return moveList.moveList.size
    }

    fun begin(): Move {
        return moveList.moveList.elementAt(0)
    }

    fun last(): Move {
        return moveList.moveList.elementAt(moveList.moveList.size - 1)
    }

    fun contains(m: Move): Boolean {
        return moveList.moveList.contains(m)
    }

    //iterator
    operator fun iterator(): Iterator<Move> {
        return moveList.moveList.iterator()
    }


}

class Movegen {


    companion object {
        fun generateMoves(
            pos: Position,
            moveList: ExtMove,
            target: Bitboard,
            us: Color,
            pt: PieceType,
            checks: Boolean
        ): ExtMove {
            assert(pt != PieceType.KING && pt != PieceType.PAWN)
            var bb: Bitboard = pos.pieces(us, pt)
            var bbo: BitboardObject = BitboardObject(bb)
            while (bbo.bb != 0UL) {
                val from: Square = popLsb(bbo)
                var b: Bitboard = attacksBb(from, pos.pieces(), pt) and target
                var bbo2: BitboardObject = BitboardObject(b)

                // To check, you either move freely a blocker or make a direct check.
                if (checks && (pt == PieceType.QUEEN || !(pos.blockersForKing(us.opposite()) and squareBb(from))))
                    b = b and pos.checkSquares(pt)
                while (bbo2.bb != 0UL)
                    moveList.moveList.add(makeMove(from, popLsb(bbo2)))

            }
            return moveList
        }

        fun generate(pos: Position, moveList: ExtMove, type: GenType): ExtMove {
            assert((type == EVASIONS) == (pos.checkers() != 0UL))

            moveList.clear()
            val m = generateMoves(pos,moveList,pos.pieces(),pos.sideToMove,PieceType.PAWN,pos.checkers()!=0UL)
            moveList.moveList.addAll(m.moveList)
            return moveList


        }

    }

}