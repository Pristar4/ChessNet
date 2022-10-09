package chessnet

import chessnet.Square.*
import chessnet.PieceType.*
import chessnet.Color.*


class Bitboards {
    val bitBoard: ULong = 0UL

    companion object {
        fun init() {}


        fun print(b: Bitboard) {
            // val b = SquareBB[0]
            println("Bitboard: $b")
            //print 8*8 board
            for (i in 0..63) {
                if (i % 8 == 0) println()
                if (b and (1UL shl i) != 0UL) print("1") else print("0")
            }
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
val KingSIde: Bitboard = FileEBB or FileFBB or FileGBB or FileHBB
val Center: Bitboard = (FileDBB or FileEBB) and (Rank4BB or Rank5BB)

val PopCnt16: Array<UByte> = Array(65536) { 0U }


/*

val SquareDistance: Array<Array<UByte>> =    Array(SQUARE_NB.value()) { Array(SQUARE_NB.value()) { 0U } }



val SquareBB: Array<Bitboard> = Array(SQUARE_NB.value()) { 1UL shl it }
val BetweenBB: Array<Array<Bitboard>> =
    Array(SQUARE_NB.value()) { Array(SQUARE_NB.value()) { 0UL } }
val LineBB: Array<Array<Bitboard>> =
    Array(SQUARE_NB.value()) { Array(SQUARE_NB.value()) { 0UL } }
val PseudoAttacks: Array<Array<Bitboard>> =
    Array(PIECE_TYPE_NB.value) { Array(SQUARE_NB.value()) { 0UL } }
val PawnAttacks: Array<Array<Bitboard>> =
    Array(COLOR_NB.value ){ Array(SQUARE_NB.value()) { 0UL } }


*/


