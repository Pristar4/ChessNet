package chessnet.movegen

import chessnet.*
import chessnet.PieceType.*
import chessnet.CastlingRights.*
import java.util.Vector


enum class GenType {
    CAPTURES, QUIETS, QUIET_CHECKS, EVASIONS, NON_EVASIONS, LEGAL,
}


class MoveList {
    var moveList: Vector<Move> = Vector()
    var move: Move = Move()
    var value: Int = 0


    fun Move(): Move {
        moveList.add(move)
        return move

    }

    fun operatorEq(m: Move) {
        move = m
    }

    fun clear() {
        moveList.clear()
    }


}


class Movegen {


    companion object {

        private fun makePromotions(
            moveList: MoveList,
            to: Square,
            direction: Direction,
            type: GenType,
        ): MoveList {
            if (type == GenType.CAPTURES || type == GenType.EVASIONS || type == GenType.NON_EVASIONS) {
                moveList.moveList.add(Move.make(to - direction, to, QUEEN, MoveType.PROMOTION))
            }
            if (type == GenType.QUIETS || type == GenType.EVASIONS || type == GenType.NON_EVASIONS) {
                moveList.moveList.add(Move.make(to - direction, to, ROOK, MoveType.PROMOTION))
                moveList.moveList.add(Move.make(to - direction, to, BISHOP, MoveType.PROMOTION))
                moveList.moveList.add(Move.make(to - direction, to, KNIGHT, MoveType.PROMOTION))
            }
            return moveList

        }


        fun generatePawnMoves(
            pos: Position,
            _moveList: MoveList,
            target: Bitboard,
            Us: Color,
            Type: GenType,
        ): MoveList {
            val Them: Color = Us.opposite()
            var us = Us
            var ExMoves = _moveList
            val TRank7BB: Bitboard = (if (Us == Color.WHITE) Rank7BB else Rank2BB)
            val TRank3BB: Bitboard = (if (Us == Color.WHITE) Rank3BB else Rank6BB)
            val Up: Direction = pawnPush(Us)
            val UpRight: Direction =
                if (Us == Color.WHITE) Direction.NORTH_EAST else Direction.SOUTH_WEST
            val UpLeft: Direction =
                if (Us == Color.WHITE) Direction.NORTH_WEST else Direction.SOUTH_EAST

            val emptySquares: Bitboard = pos.pieces().inv()
            val enemies: Bitboard =
                if (Type == GenType.EVASIONS) pos.checkers() else pos.pieces(Them)

            var pawnsOn7: Bitboard = pos.pieces(Us, PAWN) and TRank7BB
            var pawnsNotOn7: Bitboard = pos.pieces(Us, PAWN) and TRank7BB.inv()

            // Single and double pawn pushes, no promotions
            if (Type != GenType.CAPTURES) {
                var b1: Bitboard = shift(pawnsNotOn7, Up) and emptySquares
                var b2: Bitboard = shift(b1 and TRank3BB, Up) and emptySquares

                if (Type == GenType.EVASIONS) { // Consider only blocking squares
                    b1 = b1 and target
                    b2 = b2 and target
                }
                if (Type == GenType.QUIET_CHECKS) {/* To make a quiet check, you either make a direct check by pushing a pawn
                     * or push a blocker pawn that is not on the same file as the enemy king.
                     * Discovered check promotion has been already generated amongst the captures.
                     */
                    var ksq: Square = pos.square(KING, Them)
                    var dcCandidatePawns: Bitboard = pos.blockersForKing(Them) and fileBb(ksq).inv()
                    b1 = b1 and pawnAttacksBb(Them, ksq.value) or shift(dcCandidatePawns, Up)
                    b2 = b2 and pawnAttacksBb(Them, ksq.value) or shift(dcCandidatePawns, (Up + Up))
                }
                while (b1 != 0UL) {
                    val to: Square = popLsb(b1)
                    ExMoves.moveList.add(makeMove(to - Up, to))
                }
                while (b2 != 0UL) {
                    val to: Square = popLsb(b2)
                    ExMoves.moveList.add(makeMove(to - (Up + Up), to))
                }

            }
            // Promotions and underpromotions
            if (pawnsOn7 != 0UL) {
                var b1: Bitboard = shift(pawnsOn7, (UpRight)) and enemies
                var b2: Bitboard = shift(pawnsOn7, (UpLeft)) and enemies
                var b3: Bitboard = shift(pawnsOn7, (Up)) and emptySquares
                if (Type == GenType.EVASIONS) {
                    b3 = b3 and target

                }
                if (Type == GenType.QUIET_CHECKS) {/* to make a quiet check, you eithe rmake a direct check by pushing a pawn
                     * or push a blocker pawn that is not on the same file as the enemy king.
                     * Discovered check promotion has been already generated amongst the captures.
                     */
                    val ksq: Square = pos.square(KING, Them)
                    val dcCandidatePawns: Bitboard = pos.blockersForKing(Them) and fileBb(ksq).inv()
                    b1 = b1 and pawnAttacksBb(Them, ksq.value) or shift(dcCandidatePawns, Up)
                    b2 = b2 and pawnAttacksBb(Them, ksq.value) or shift(dcCandidatePawns, Up + Up)
                }
                while (b1 != 0UL) {
                    val to: Square = popLsb(b1)
                    ExMoves = makePromotions(ExMoves, popLsb(b1), UpRight, Type)

                }
                while (b2 != 0UL) {
                    ExMoves = makePromotions(ExMoves, popLsb(b2), UpLeft, Type)

                }
                while (b3 != 0UL) {
                    ExMoves = makePromotions(ExMoves, popLsb(b3), Up, Type)

                }

            }
            // Standard and en-passant captures
            if (Type == GenType.CAPTURES || Type == GenType.EVASIONS || Type == GenType.NON_EVASIONS) {
                var b1: Bitboard = shift(pawnsNotOn7, (UpRight)) and enemies
                var b2: Bitboard = shift(pawnsNotOn7, UpLeft) and enemies

                while (b1 != 0UL) {
                    val to: Square = popLsb(b1)
                    ExMoves.moveList.add(makeMove(to - UpRight, to))


                }
                while (b2 != 0UL) {
                    val to: Square = popLsb(b2)
                    ExMoves.moveList.add(makeMove(to - UpLeft, to))
                }
                if (pos.epSquare() != Square.SQ_NONE) {
                    assert(rankOf(pos.epSquare()) == relativeRank(Us, Rank.RANK_6))

                    // An en passant capture cannot resolve a discovered check
                    if (Type == GenType.EVASIONS && (target and (SquareBB[pos.epSquare().value] + Up.value.toULong())) == 0UL) {
                        return ExMoves
                    }
                    b1 = pawnsNotOn7 and pawnAttacksBb(Them, pos.epSquare().value)
                    assert(b1 != 0UL)
                    while (b1 != 0UL) {
                        ExMoves.moveList.add(
                            Move.make(
                                popLsb(b1), pos.epSquare(), KNIGHT, MoveType.EN_PASSANT
                            )
                        )
                    }
                }
            }

            return ExMoves
        }

        fun generate(pos: Position, moveList: MoveList, type: GenType): MoveList {
            var us: Color = pos.sideToMove
            var pinned: Bitboard = pos.blockersForKing(us) and pos.pieces(us)
            var ksq: Square = pos.square(KING, us)
            var cur: MoveList = moveList

            moveList.clear()



            generateAll(pos, moveList, us, type)


            return moveList

        }


        fun generateMoves(
            pos: Position,
            moveList: MoveList,
            target: Bitboard,
            Us: Color,
            type: PieceType,
            checks: Boolean,
        ): MoveList {

            return moveList


        }


        fun generateAll(pos: Position, _moveList: MoveList, Us: Color, Type: GenType): MoveList {
            var moveList: MoveList = _moveList

            assert(Type != GenType.LEGAL, { "generateAll(): Type == LEGAL is not supported" })
            var Checks: Boolean = Type == GenType.QUIET_CHECKS
            val ksq: Square = pos.square(KING, Us)
            println("ksq: $ksq")
            var target: Bitboard = 0UL

            // Skip generating non-king moves when in double check
            if (Type != GenType.EVASIONS || !moreThanOne(pos.checkers())) {
                target = when (Type) {
                    GenType.EVASIONS -> betweenBb(ksq, lsb(pos.checkers()))
                    GenType.NON_EVASIONS -> pos.pieces(Us).inv()
                    GenType.CAPTURES -> pos.pieces(Color.values()[Us.value xor 1])
                    else -> pos.pieces().inv() // QUIETS || QUIET_CHECKS


                }
                moveList = generatePawnMoves(pos, moveList, target, Us, Type)
                moveList = generateMoves(pos, moveList, target, Us, KNIGHT, Checks)
                moveList = generateMoves(pos, moveList, target, Us, BISHOP, Checks)
                moveList = generateMoves(pos, moveList, target, Us, ROOK, Checks)
                moveList = generateMoves(pos, moveList, target, Us, QUEEN, Checks)
            }
            if (!Checks || pos.blockersForKing(Us.opposite()) and ksq.value.toULong() == 0UL) {
                var b = attacksBb(KING, ksq) and (if (Type == GenType.EVASIONS) pos.pieces(Us)
                    .inv() else target)
                println(
                    b
                )


                if (Checks) {
                    b = b and attacksBb(
                        QUEEN, pos.square(KING, Color.values()[Us.value xor 1]), pos.pieces()
                    )
                }
                while (b != 0UL) {
                    moveList.moveList.add(makeMove(ksq, popLsb(b)))
                }
                if ((Type == GenType.QUIETS || Type == GenType.NON_EVASIONS) && pos.canCastle(Us.value and ANY_CASTLING.value)) {
                    for (cr in listOf(
                        Us.value and KING_SIDE.value,
                        Us.value and QUEEN_SIDE.value
                    )) {
                        if (!pos.castlingImpeded(CastlingRights.values()[cr]) && pos.canCastle(cr)) { //TODO: check if this is correct
                            moveList.moveList.add(
                                Move.make(
                                    ksq,
                                    pos.castlingRookSquare(CastlingRights.values()[cr]),
                                    KING,
                                    MoveType.CASTLING
                                )
                            )
                        }
                    }

                }
            }
            return moveList

        }
    }
}


