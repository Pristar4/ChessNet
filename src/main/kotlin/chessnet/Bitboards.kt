package chessnet

import chessnet.Color.*
import chessnet.PieceType.*
import chessnet.Square.*
import chessnet.Direction.*


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
            for (s1 in 0..63) {/*PawnAttacks[Color.WHITE.value][s1] =
                    pawnAttacksBb(Color.WHITE, s1)
                PawnAttacks[Color.BLACK.value][s1] =
                    pawnAttacksBb(Color.BLACK, s1)*/

                for (step in listOf(-9, -8, -7, -1, 1, 7, 8, 9)) {

                    PseudoAttacks[KING.value][s1] =
                        PseudoAttacks[KING.value][s1] or safeDestination(
                            s1, step
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
            println(b)
            println("pretty")
            var s = "+---+---+---+---+---+---+---+---+\n"
            for (r in Rank.RANK_8.value downTo Rank.RANK_1.value) {
                for (f in File.FILE_A.value..File.FILE_H.value) {
                    s += if (b and SquareBB[makeSquare(f,r).value] != 0UL) "| X " else "|   "
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


val SquareBB: Array<Bitboard> = Array(SQUARE_NB.value) { 0UL }
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
    val shift: Int,
) {
    fun index(occupied: Bitboard): Int {
        //TODO: check if this is correct
        return ((occupied and mask) * magic ushr shift).toInt()

    }
}

val RookMagics: Array<Magic> =
    Array(SQUARE_NB.value) { Magic(0UL, 0UL, Array(4096) { 0UL }, 0) }
val BishopMagics: Array<Magic> =
    Array(SQUARE_NB.value) { Magic(0UL, 0UL, Array(512) { 0UL }, 0) }


/// distance() functions return the distance between x and y, defined as the
/// number of steps for a king in x to reach y.

fun squareBb(s: Square): Bitboard {
    assert(isOk(s))
    return SquareBB[s.value]

}

/* Overloads of bitwise operators between a Bitboard and a Square for testing
 * whether a given bit is set in a bitboard, and for setting and clearing bits.
 */

operator fun Bitboard.get(s: Square): Boolean {
    assert(isOk(s))
    return this and squareBb(s) != 0UL
}

operator fun ULong.not(): Boolean {
    return this == 0UL

}

fun moreThanOne(b: Bitboard): Boolean {
    return (b and (b - 1u)) != 0UL

}


/*  rankBb() and fileBb() return a bitboard representing all the squares on the
 * given file or rank.
 */

fun rankBb(r: Rank): Bitboard {
    return (Rank1BB shl (8 * r.ordinal))
}

fun rankBb(s: Square): Bitboard {

    return rankBb(rankOf(s))
}

fun fileBb(f: File): Bitboard {
    return (FileABB shl f.ordinal)
}

fun fileBb(s: Square): Bitboard {

    return fileBb(fileOf(s))
}

// shift() moves a bitboard one or two steps as specified by the given direction D

fun shift(b: Bitboard, d: Any): Bitboard {
    return when (d) {
        NORTH -> b shl 8
        SOUTH -> b shr 8
        NORTH + NORTH -> b shl 16
        SOUTH + SOUTH -> b shr 16
        EAST -> (b and FileHBB.inv()) shl 1
        WEST -> (b and FileABB.inv()) shr 1
        NORTH_EAST -> (b and FileHBB.inv()) shl 9
        NORTH_WEST -> (b and FileABB.inv()) shl 7
        SOUTH_EAST -> (b and FileHBB.inv()) shr 7
        SOUTH_WEST -> (b and FileABB.inv()) shr 9
        else -> 0UL


        //TODO: check if this is correct
    }
}

/* line_bb() returns a bitboard representing an entire line (from board edge
*  to board edge) that intersects the two given squares. If the given squares
*  are not on a same file/rank/diagonal, the function returns 0. For instance,
*  line_bb(SQ_C4, SQ_F7) will return a bitboard with the A2-G8 diagonal.
* */
fun lineBb(s1: Square, s2: Square): Bitboard {
    assert(isOk(s1) && isOk(s2))
    return LineBB[s1.ordinal][s2.ordinal]

}

fun betweenBb(s1: Square, s2: Square): Bitboard {
    assert(isOk(s1) && isOk(s2))
    return LineBB[s1.ordinal][s2.ordinal]


}

fun aligned(s1: Square, s2: Square, s3: Square): Bitboard {
    return lineBb(s1, s2) and squareBb(s3)
}


inline fun <reified T> distance(
    x: Int, y: Int,
): Int { //inline to improve performance (no runtime overhead)
    // Sexy Kotlin code to get the distance between two squares on the same rank or file
    return when (T::class) {
        File::class -> kotlin.math.abs(x / BOARD_SIZE - y / BOARD_SIZE)
        Rank::class -> kotlin.math.abs(x % BOARD_SIZE - y % BOARD_SIZE)
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

fun attacksBb(Pt: PieceType, s: Square): Bitboard {
    assert((Pt != PAWN) && (isOk(s)))

    //TODO: check if this is correct
    return PseudoAttacks[Pt.ordinal][s.ordinal - 1]

}


// function that returns the pseudo attacks of the give piece type

fun attacksBb(
    Pt: PieceType,
    s1: Square, occupied: Bitboard,
): Bitboard {
    return when (Pt) {
        BISHOP -> BishopMagics[s1.value].attacks[BishopMagics[s1.value].index(
            occupied
        )]

        ROOK -> RookMagics[s1.value].attacks[RookMagics[s1.value].index(occupied)]
        QUEEN -> {

            attacksBb(BISHOP, s1, occupied) or attacksBb(ROOK, s1, occupied)
        }

        else -> PseudoAttacks[Pt.value][s1.value]
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
fun safeDestination(s: Int, step: Int): Bitboard {
    val to: Square = Square.getSquare(s + step)

    return if (isOk(to) && distance<Square>(
            s, to.value
        ) <= 2
    ) squareBb(to) else Bitboard.MIN_VALUE


}


fun lsb(b: Bitboard): Square {
    assert(b != 0UL)
    val idx: Int = BitScanForward(b)


    return Square.getSquare(idx)


}


fun msb(b: Bitboard): Square {
    assert(b != 0UL)
    val idx: Int = BitScanReverse(b)
    return Square.getSquare(idx)
}

fun popLsb(bitBoardObject: BitboardObject): Square {
    //FIXME:  popLsb() is not working correctly (doesnt change the bitboard)
    assert(bitBoardObject.bb != 0UL)
    val s: Square = lsb(bitBoardObject.bb)
    bitBoardObject.bb = bitBoardObject.bb and (bitBoardObject.bb - 1u)
    return s
}

fun BitScanForward(b: Bitboard): Int {
    var idx: Int = 0
    while (b and (1UL shl idx) == 0UL) {
        idx++
    }
    return idx
}

fun BitScanReverse(b: Bitboard): Int {
    var idx: Int = 63
    while (b and (1UL shl idx) == 0UL) {
        idx--
    }
    return idx
}