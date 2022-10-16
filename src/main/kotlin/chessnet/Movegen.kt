package chessnet.movegen

import chessnet.*
import chessnet.PieceType.*
import chessnet.Color.*
import chessnet.CastlingRights.*
import java.util.Vector
import chessnet.movegen.GenType.*

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
            moveList: ExtMove,
            to: Square,
            direction: Direction,
            type: GenType,
        ): ExtMove {
            if (type == GenType.CAPTURES || type == GenType.EVASIONS || type == GenType.NON_EVASIONS) {
                moveList.moveList.add(
                    Move.make(
                        to - direction, to, QUEEN, MoveType.PROMOTION
                    )
                )
            }
            if (type == GenType.QUIETS || type == GenType.EVASIONS || type == GenType.NON_EVASIONS) {
                moveList.moveList.add(
                    Move.make(
                        to - direction, to, ROOK, MoveType.PROMOTION
                    )
                )
                moveList.moveList.add(
                    Move.make(
                        to - direction, to, BISHOP, MoveType.PROMOTION
                    )
                )
                moveList.moveList.add(
                    Move.make(
                        to - direction, to, KNIGHT, MoveType.PROMOTION
                    )
                )
            }
            return moveList

        }


        fun generatePawnMoves(
            pos: Position,
            _moveList: ExtMove,
            target: Bitboard,
            Us: Color,
            Type: GenType,
        ): ExtMove {
            val Them: Color = Us.opposite()
            var us = Us
            var ExtMoves = _moveList
            val TRank7BB: Bitboard =
                (if (Us == Color.WHITE) Rank7BB else Rank2BB)
            val TRank3BB: Bitboard =
                (if (Us == Color.WHITE) Rank3BB else Rank6BB)
            val Up: Direction = pawnPush(Us)
            val UpRight: Direction =
                if (Us == Color.WHITE) Direction.NORTH_EAST else Direction.SOUTH_WEST
            val UpLeft: Direction =
                if (Us == Color.WHITE) Direction.NORTH_WEST else Direction.SOUTH_EAST

            val emptySquares: Bitboard = pos.pieces().inv()
            val enemies: Bitboard =
                if (Type == GenType.EVASIONS) pos.checkers() else pos.pieces(
                    Them
                )

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
                    var dcCandidatePawns: Bitboard =
                        pos.blockersForKing(Them) and fileBb(ksq).inv()
                    b1 = b1 and pawnAttacksBb(Them, ksq.value) or shift(
                        dcCandidatePawns, Up
                    )
                    b2 = b2 and pawnAttacksBb(Them, ksq.value) or shift(
                        dcCandidatePawns, (Up + Up)
                    )
                }
                while (b1 != 0UL) {
                    var (to, b1_) = popLsb(b1)
                    ExtMoves.moveList.add(makeMove(to - Up, to))
                    b1 = b1_


                }
                while (b2 != 0UL) {
                    val (to, b2_) = popLsb(b2)
                    ExtMoves.moveList.add(makeMove(to - (Up + Up), to))
                    b2 = b2_
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
                    val dcCandidatePawns: Bitboard =
                        pos.blockersForKing(Them) and fileBb(ksq).inv()
                    b1 = b1 and pawnAttacksBb(Them, ksq.value) or shift(
                        dcCandidatePawns, Up
                    )
                    b2 = b2 and pawnAttacksBb(Them, ksq.value) or shift(
                        dcCandidatePawns, Up + Up
                    )
                }
                while (b1 != 0UL) {
                    val (to, b1_) = popLsb(b1)

                    ExtMoves = makePromotions(ExtMoves, to, UpRight, Type)
                    b1 = b1_

                }
                while (b2 != 0UL) {
                    val (to, b2_) = popLsb(b2)
                    ExtMoves = makePromotions(ExtMoves, to, UpLeft, Type)
                    b2 = b2_

                }
                while (b3 != 0UL) {
                    val (to, b3_) = popLsb(b3)
                    ExtMoves = makePromotions(ExtMoves, to, Up, Type)
                    b3 = b3_

                }

            }
            // Standard and en-passant captures
            if (Type == GenType.CAPTURES || Type == GenType.EVASIONS || Type == GenType.NON_EVASIONS) {
                var b1: Bitboard = shift(pawnsNotOn7, (UpRight)) and enemies
                var b2: Bitboard = shift(pawnsNotOn7, UpLeft) and enemies

                while (b1 != 0UL) {
                    val (to, b1_) = popLsb(b1)
                    ExtMoves.moveList.add(makeMove(to - UpRight, to))
                    b1 = b1_


                }
                while (b2 != 0UL) {
                    val (to, b2_) = popLsb(b2)
                    ExtMoves.moveList.add(makeMove(to - UpLeft, to))
                    b2 = b2_
                }
                if (pos.epSquare() != Square.SQ_NONE) {
                    assert(
                        rankOf(pos.epSquare()) == relativeRank(
                            Us, Rank.RANK_6
                        )
                    )

                    // An en passant capture cannot resolve a discovered check
                    if (Type == GenType.EVASIONS && (target and (SquareBB[pos.epSquare().value] + Up.value.toULong())) == 0UL) {
                        return ExtMoves
                    }
                    b1 = pawnsNotOn7 and pawnAttacksBb(
                        Them, pos.epSquare().value
                    )
                    assert(b1 != 0UL)
                    while (b1 != 0UL) {
                        val (to, b1_) = popLsb(b1)
                        ExtMoves.moveList.add(
                            Move.make(
                                to, pos.epSquare(), KNIGHT, MoveType.EN_PASSANT
                            )
                        )
                        b1 = b1_
                    }
                }
            }

            return ExtMoves
        }


       /* fun generate(
            pos: Position,
            moveList: StateInfo,
            type: GenType,
        ): ExtMove {
            assert(type != LEGAL, { "Unsupported type in generate()" })
            assert((type == EVASIONS) == (pos.checkers() != 0UL))

            var us: Color = pos.sideToMove

            return when (us) {
                WHITE -> generateAll(pos, moveList, WHITE, type)
                BLACK -> generateAll(pos, moveList, BLACK, type)
                else -> {
                    println("Error in generate")
                    ExtMove()
                }
            }
        }*/



        fun generateMoves(
            pos: Position,
            moveList: ExtMove,
            target: Bitboard,
            Us: Color,
            type: PieceType,
            checks: Boolean,
        ): ExtMove {
            assert(type != KING && type != PAWN,
                { "generateMoves(): bad piece type" })
            var bb: Bitboard = pos.pieces(Us, type)

            while (bb != 0UL) {
                val (from, bb_) = popLsb(bb)
                var b: Bitboard = attacksBb(type, from, target)
                while (b != 0UL) {
                    val (to, b_) = popLsb(b)
//                    moveList.add(makeMove(from, to))
                    moveList.moveList.add(makeMove(from, to))
                    b = b_
                }
                bb = bb_
            }

            return moveList


        }


        fun generateAll(
            pos: Position, _moveList: StateInfo, Us: Color, Type: GenType
        ): ExtMove {
            var moveList: ExtMove = ExtMove()

            assert(Type != GenType.LEGAL,
                { "generateAll(): Type == LEGAL is not supported" })
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
                moveList =
                    generateMoves(pos, moveList, target, Us, KNIGHT, Checks)
                moveList =
                    generateMoves(pos, moveList, target, Us, BISHOP, Checks)
                moveList =
                    generateMoves(pos, moveList, target, Us, ROOK, Checks)
                moveList =
                    generateMoves(pos, moveList, target, Us, QUEEN, Checks)
            }
            if (!Checks || pos.blockersForKing(Us.opposite()) and ksq.value.toULong() == 0UL) {
                var b = attacksBb(
                    KING, ksq
                ) and (if (Type == GenType.EVASIONS) pos.pieces(Us)
                    .inv() else target)
                println(
                    b
                )


                if (Checks) {
                    b = b and attacksBb(
                        QUEEN,
                        pos.square(KING, Color.values()[Us.value xor 1]),
                        pos.pieces()
                    )
                }
                while (b != 0UL) {
                    val (to, b_) = popLsb(b)
                    moveList.moveList.add(makeMove(ksq, to))
                    b = b_
                }
                if ((Type == GenType.QUIETS || Type == GenType.NON_EVASIONS) && pos.canCastle(
                        Us.value and ANY_CASTLING.value
                    )
                ) {
                    for (cr in listOf(
                        Us.value and KING_SIDE.value,
                        Us.value and QUEEN_SIDE.value
                    )) {
                        if (!pos.castlingImpeded(CastlingRights.values()[cr]) && pos.canCastle(
                                cr
                            )
                        ) { //TODO: check if this is correct
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

