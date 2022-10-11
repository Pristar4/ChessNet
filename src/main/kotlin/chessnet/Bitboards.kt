package chessnet

import chessnet.Color.Companion.COLOR_NB
import chessnet.PieceType.*
import chessnet.Square.SQUARE_NB


class Bitboards {
    val bitBoard: ULong = 0UL

    companion object {

        fun init() {


            for (s in 0..63) {
                SquareBB[s] = 1UL shl s
            }
            for (s1 in 0..63) {
                for (s2 in 0..63) {
                    SquareDistance[s1][s2] = maxOf(
                        distance<File>(s1, s2), distance<Rank>(s1, s2)
                    ).toUByte()
                }
            }

            //init_magics(ROOK,RookTable,RookMagics);
            //init_magics(BISHOP,BishopTable,BishopMagics);
            for (s1 in 0..63) {
                /*PawnAttacks[Color.WHITE.value][s1] =
                    pawnAttacksBb(Color.WHITE, s1)
                PawnAttacks[Color.BLACK.value][s1] =
                    pawnAttacksBb(Color.BLACK, s1)*/

                for (step in listOf(-9, -8, -7, -1, 1, 7, 8, 9)) {
                    PseudoAttacks[KING.value][s1] =
                        PseudoAttacks[KING.value][s1] or safeDestination(
                            Square.getSquare(s1), step
                        )


                }

                /* for (step in listOf(-17, -15, -10, -6, 6, 10, 15, 17)) {
                     PseudoAttacks[KNIGHT.value][s1] =
                         PseudoAttacks[KNIGHT.value][s1] or safeDestination(
                             Square.getSquare(s1), step
                         )

                 }

                 PseudoAttacks[PieceType.BISHOP.value][s1] =
                    attacksBb(BISHOP, Square.getSquare(s1), 0u)

 */
            }


        }

        /* Bitboards::pretty() returns an ASCII representation of a bitboard suitable
            * to be printed to standard output. Useful for debugging.
            */
        fun pretty(b: Bitboard): String {
            println("pretty")
            var s = "+---+---+---+---+---+---+---+---+\n"
            for (r in Rank.RANK_8.ordinal downTo Rank.RANK_1.ordinal) {
                for (f in File.FILE_A.ordinal..File.FILE_H.ordinal) {
                    s += if (b and SquareBB[Square(f, r)] != 0UL) "| X " else "|   "

                }
                    s += "|\n+---+---+---+---+---+---+---+---+\n"

            }
                s += "  a   b   c   d   e   f   g   h\n"
            return s

        }

    }


}


const val ALL_SQUARES: Bitboard = ULong.MAX_VALUE
const val DARK_SQUARES: Bitboard = 0xAA55AA55AA55AA55UL
const val FileABB: Bitboard = 0x0101010101010101UL
val FileBBB: Bitboard = FileABB shl 1
val FileCBB: Bitboard = FileABB shl 2
val FileDBB: Bitboard = FileABB shl 3
val FileEBB: Bitboard = FileABB shl 4
val FileFBB: Bitboard = FileABB shl 5
val FileGBB: Bitboard = FileABB shl 6
val FileHBB: Bitboard = FileABB shl 7

const val Rank1BB: Bitboard = 0xFFUL
val Rank2BB: Bitboard = 0xFFUL shl (8 * 1)
val Rank3BB: Bitboard = 0xFFUL shl (8 * 2)
val Rank4BB: Bitboard = 0xFFUL shl (8 * 3)
val Rank5BB: Bitboard = 0xFFUL shl (8 * 4)
val Rank6BB: Bitboard = 0xFFUL shl (8 * 5)
val Rank7BB: Bitboard = 0xFFUL shl (8 * 6)
val Rank8BB: Bitboard = 0xFFUL shl (8 * 7)

// A B C D E F G H
// 1 2 3 4 5 6 7 8
val QueenSide: Bitboard = FileABB or FileBBB or FileCBB or FileDBB
val CenterFiles: Bitboard = FileCBB or FileDBB or FileEBB or FileFBB
val KingSide: Bitboard = FileEBB or FileFBB or FileGBB or FileHBB
val Center: Bitboard = (FileDBB or FileEBB) and (Rank4BB or Rank5BB)

val PopCnt16: Array<UByte> = Array(65536) { 0U }


val SquareDistance: Array<Array<UByte>> =
    Array(SQUARE_NB.value) { Array(SQUARE_NB.value) { 0U } }


val SquareBB: Array<Bitboard> = Array(SQUARE_NB.value) { 1UL shl it }
val BetweenBB: Array<Array<Bitboard>> =
    Array(SQUARE_NB.value) { Array(SQUARE_NB.value) { 0UL } }
val LineBB: Array<Array<Bitboard>> =
    Array(SQUARE_NB.value) { Array(SQUARE_NB.value) { 0UL } }
val PseudoAttacks: Array<Array<Bitboard>> =
    Array(PIECE_TYPE_NB.value) { Array(SQUARE_NB.value) { 0UL } }
val PawnAttacks: Array<Array<Bitboard>> =
    Array(COLOR_NB.value) { Array(SQUARE_NB.value) { 0UL } }

class Magic(
    val mask: Bitboard,
    val magic: Bitboard,
    val attacks: Array<Bitboard>,
    val shift: Int
)

val RookMagics: Array<Magic> =
    Array(SQUARE_NB.value) { Magic(0UL, 0UL, Array(4096) { 0UL }, 0) }
val BishopMagics: Array<Magic> =
    Array(SQUARE_NB.value) { Magic(0UL, 0UL, Array(512) { 0UL }, 0) }


/// distance() functions return the distance between x and y, defined as the
/// number of steps for a king in x to reach y.

private fun squareBb(s1: Square): Bitboard {
//    assert(is_ok(s))
    return SquareBB[s1.value]

}

inline fun <reified T> distance(
    x: Int, y: Int
): Int { //inline to improve performance (no runtime overhead)
    // Sexy Kotlin code to get the distance between two squares on the same rank or file
    return when (T::class) {
        File::class -> kotlin.math.abs(x / 8 - y / 8)
        Rank::class -> kotlin.math.abs(x % 8 - y % 8)
        Square::class -> SquareDistance[x][y].toInt()
        else -> throw Exception("Unknown type")
    }
}

fun pawnAttacksBb(c: Color, s1: Int): Bitboard {
    return when (c) {
        Color.WHITE -> SquareBB[s1 + 8] or SquareBB[s1 + 16]
        Color.BLACK -> SquareBB[s1 - 8] or SquareBB[s1 - 16]
        else -> {
            throw Exception("Unknown color")
        }
    }


}


/// attacks_bb(Square) returns the pseudo attacks of the give piece type
/// assuming an empty board.
inline fun <reified Pt : PieceType> attacksBb(s1: Square): Bitboard {
    return when (Pt::class) {
        PieceType.KING::class -> PseudoAttacks[KING.value][s1.value]
        PieceType.KNIGHT::class -> PseudoAttacks[KNIGHT.value][s1.value]
        PieceType.BISHOP::class -> PseudoAttacks[BISHOP.value][s1.value]
        PieceType.ROOK::class -> PseudoAttacks[ROOK.value][s1.value]
        PieceType.QUEEN::class -> PseudoAttacks[QUEEN.value][s1.value]
        else -> throw Exception("Unknown piece type")
    }

}

// function that returns the pseudo attacks of the give piece type

inline fun attacksBb(
    Pt: PieceType,
    s1: Square, occupied: Bitboard
): Bitboard {
    return when (Pt::class) {
        PieceType.BISHOP::class -> bishopAttacksBb(s1, occupied)
        PieceType.ROOK::class -> rookAttacksBb(s1, occupied)
        PieceType.QUEEN::class -> bishopAttacksBb(
            s1,
            occupied
        ) or rookAttacksBb(s1, occupied)

        else -> throw Exception("Unknown piece type")
    }

}

fun rookAttacksBb(s: Square, occupied: Bitboard): Bitboard {
    val magic = RookMagics[s.value]
    val index = ((occupied and magic.mask) * magic.magic) ushr magic.shift
    return magic.attacks[index.toInt()]

}

fun bishopAttacksBb(s: Square, occupied: Bitboard): Bitboard {
    val magic = BishopMagics[s.value]
    val index = ((occupied and magic.mask) * magic.magic) ushr magic.shift
    return magic.attacks[index.toInt()]

}

private fun Any.toInt(): Int {
    return this as Int

}

private infix fun Any.ushr(shift: Int): Any {
    return when (this) {
        is Int -> this ushr shift
        is Long -> this ushr shift
        else -> throw Exception("Unknown type")
    }
}


// TODO: check if this should be here or in companion object
fun safeDestination(s: Square, step: Int): Bitboard {
    val to = Square.getSquare(
        s.value + step
    )
//            return (isOk(to) && if (distance() <= 2) squareBb(to) else Bitboard.MIN_VALUE )
    val dist = distance<Square>(s.value, to.value)
    return if (isOk(to) && dist <= 2) squareBb(to) else Bitboard.MIN_VALUE


}