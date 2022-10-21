package chessnet

import java.util.Vector
import chessnet.GenType.*
import chessnet.PieceType.*
import chessnet.CastlingRights.*
import chessnet.MoveType.*
import chessnet.Movegen.Companion.generate

enum class GenType {
    CAPTURES, QUIETS, QUIET_CHECKS, EVASIONS, NON_EVASIONS, LEGAL,
}


class ExtMove(val size: Int = 0) {
    var moveList: Vector<Move> = Vector()
    var move: Move = Move.MOVE_NONE
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
    var moves: ExtMove


    private val moveList: ExtMove = ExtMove()

    init {
        moves = if (type == LEGAL) {
            generate(pos, moveList)
        } else {
            generate(pos, moveList, type)
        }
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
        fun generatePawnMoves(pos: Position, moveList: ExtMove, target: Bitboard, us: Color, type: GenType): ExtMove {
            return TODO()

        }

        fun generateMoves(
            pos: Position, moveList: ExtMove, target: Bitboard, us: Color, pt: PieceType, checks: Boolean
        ): ExtMove {
            assert(pt != KING && pt != PAWN)
            var bb: Bitboard = pos.pieces(us, pt)
            var bbo = BitboardObject(bb)
            while (bbo.bb != 0UL) {
                val from: Square = popLsb(bbo)
                var b: Bitboard = attacksBb(from, pos.pieces(), pt) and target
                var bbo2 = BitboardObject(b)

                // To check, you either move freely a blocker or make a direct check.
                if (checks && (pt == QUEEN || !(pos.blockersForKing(us.opposite()) and squareBb(from))))
                    b = b and pos.checkSquares(pt)
                while (bbo2.bb != 0UL)
                    moveList.moveList.add(makeMove(from, popLsb(bbo2)))

            }
            return moveList
        }

        fun generateAll(pos: Position, _moveList: ExtMove, us: Color, type: GenType): ExtMove {
            var moveList: ExtMove = _moveList
            assert(type != LEGAL) { "generateAll(): Unsupported type" }
            val checks: Boolean = type == QUIET_CHECKS // Only generate checks in this case
            val ksq: Square = pos.square(KING, us)
            var target: Bitboard = if (type == EVASIONS) pos.checkers() else pos.pieces()

            // Skip generating non-king moves when in double check
            if (type != EVASIONS || !moreThanOne(pos.checkers())) {
              /*  target = Type == EVASIONS       ? between_bb(ksq, lsb(pos.checkers()))
                : Type == NON_EVASIONS ? ~pos.pieces(Us)
                : Type == CAPTURES     ? pos.pieces(~Us)
                : ~pos.pieces(); // QUIETS || QUIET_CHECKS in kotlin*/
                target = when (type) {
                    EVASIONS -> {
                        betweenBb(ksq, lsb(pos.checkers()))
                    }
                    NON_EVASIONS -> {
                        // ~pos.pieces(Us)
                        pos.pieces(us).inv()
                    }
                    CAPTURES -> {
                        pos.pieces(us.opposite())
                    }
                    else -> {
                        pos.pieces().inv()
                    }
                }
//                        println(Bitboards.pretty(target))


//                moveList = generatePawnMoves(pos, moveList, target, us, type)
                moveList = generateMoves(pos, moveList, target, us, KNIGHT, checks)
                moveList = generateMoves(pos, moveList, target, us, BISHOP, checks)
                moveList = generateMoves(pos, moveList, target, us, ROOK, checks)
                moveList = generateMoves(pos, moveList, target, us, QUEEN, checks)
            }

            if (!checks || (pos.blockersForKing(us.opposite()) and squareBb(ksq)) == 0UL) {
                val b: Bitboard = attacksBb(KING, ksq) and (if (type == EVASIONS) pos.pieces(us).inv() else target)
                val bo: BitboardObject = BitboardObject(b)
                if (checks) {
                    //TODO: generateAll(): check if using BitboardObject is correct
                    bo.bb = bo.bb and attacksBb(QUEEN, pos.square(KING, us.opposite())).inv()
//                    b = b and attacksBb(QUEEN, pos.square(KING, us.opposite())).inv()
                }
                while (bo.bb != 0UL) {
                    moveList.moveList.add(makeMove(ksq, popLsb(bo)))
                }

                if ((type == QUIETS || type == NON_EVASIONS) && pos.canCastle(us.value and ANY_CASTLING.value))
                    for (cr in listOf(us.value and KING_SIDE.value, us.value and QUEEN_SIDE.value))
                        if (!pos.castlingImpeded(CastlingRights.values()[cr]) && pos.canCastle(cr))
                            moveList.moveList.add(makeMove(ksq, pos.castlingRookSquare(CastlingRights.values()[cr])))

            }

            return moveList
        }


        fun generate(pos: Position, moveList: ExtMove, type: GenType): ExtMove {
            assert(type != LEGAL) { "generate(): type == LEGAL is not supported" }
            assert((type == EVASIONS) == (pos.checkers() != 0UL)) { "generate(): type == EVASIONS is not supported" }

            val us: Color = pos.sideToMove
            return when (us == Color.WHITE) {
                true -> generateAll(pos, moveList, Color.WHITE, type)
                false -> generateAll(pos, moveList, Color.BLACK, type)
            }
        }

        /// generate<LEGAL> generates all the legal moves in the given position

        fun generate(pos: Position, _moveList: ExtMove): ExtMove {
            var us: Color = pos.sideToMove
            var pinned: Bitboard = pos.blockersForKing(us) and pos.pieces(us)
            var ksq: Square = pos.square(KING, us)
            var moveList: ExtMove = _moveList
            var cur: ExtMove = moveList


            moveList = if (pos.checkers() != 0UL) {
                generate(pos, moveList, EVASIONS)
            } else {
                generate(pos, moveList, NON_EVASIONS)
            }
            while (cur != moveList){
                if (  ((pinned and squareBb(fromSq(cur.move))) != 0UL || fromSq(cur.move) == ksq || typeOf(cur.move) == EN_PASSANT)
                    && !pos.legal(cur.move))
                    cur.move = moveList.moveList.removeAt(moveList.moveList.size - 1)
                else
                    cur.move = moveList.moveList.elementAt(moveList.moveList.indexOf(cur.move) + 1)

            }

            return moveList

        }


    }

}