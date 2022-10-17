package chessnet

import chessnet.Square.SQ_A1
import chessnet.Square.SQ_H8


// this File hold all type definitions
typealias Key = ULong
typealias Bitboard = ULong

const val MAX_MOVES: Int = 256
const val MAX_PLY: Int = 246

class BitboardObject(var bb: Bitboard)


class Move(val value: Int = 0) {
//    var MOVE_NONE = Move(0)
//     var MOVE_NULL = Move(65)

    companion object {
        val MOVE_NONE: Move = Move(0)
        val MOVE_NULL: Move = Move(65)

        fun make(
            from: Square,
            to: Square,
            pt: PieceType = PieceType.KNIGHT,
            moveType: MoveType,
        ): Move {
            return Move(moveType.value + ((pt.value - PieceType.KNIGHT.value) shl 12) + (from.value shl 6) + to.value)

        }
    }

}

enum class MoveType(val value: Int) {
    NORMAL(0), PROMOTION(1 shl 14), EN_PASSANT(2 shl 14), CASTLING(3 shl 14)
}


enum class Color(i: Int) {
    WHITE(0), BLACK(1), COLOR_NB(2);


    fun opposite(): Color {
        return when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
            else -> throw Exception("opposite() not implemented for $this")
        }

    }

    val value: Int = i


}

enum class CastlingRights(val value: Int) {
    NO_CASTLING(0), WHITE_OO(1), WHITE_OOO(WHITE_OO.value shl 1), BLACK_OO(WHITE_OO.value shl 2), BLACK_OOO(
        WHITE_OO.value shl 3
    ),

    KING_SIDE(WHITE_OO.value or BLACK_OO.value), QUEEN_SIDE(WHITE_OOO.value or BLACK_OOO.value), WHITE_CASTLING(
        WHITE_OO.value or WHITE_OOO.value
    ),
    BLACK_CASTLING(BLACK_OO.value or BLACK_OOO.value), ANY_CASTLING(WHITE_CASTLING.value or BLACK_CASTLING.value),

    CASTLING_RIGHT_NB(16)

}

enum class Value(val value: Int) {
    VALUE_ZERO(0), VALUE_DRAW(0), VALUE_KNOWN_WIN(10000), VALUE_MATE(32000), VALUE_INFINITE(32001), VALUE_NONE(
        32002
    ),;

    operator fun unaryMinus(): Int {
        return -value

    }

//    VALUE_TB_WIN_IN_MAX_PLY
}

fun PieceType(value: Int): PieceType {
    return PieceType.values()[value]
}

enum class PieceType(val char: Char, i: Int = 0) {
    NO_PIECE_TYPE('-', 0), PAWN('p', 1), KNIGHT('n', 2), BISHOP('b', 3), ROOK('r', 4), QUEEN(
        'q', 5
    ),
    KING('k', 6), ALL_PIECES('?', 0), PIECE_TYPE_NB('#', 8);

    val value: Int = if (i == -1) ordinal else i


    override fun toString(): String {
        return char.toString()
    }
}

enum class Piece(val value: Int = 0) {
    NO_PIECE(0), W_PAWN(1), W_KNIGHT(2), W_BISHOP(3), W_ROOK(4), W_QUEEN(5), W_KING(6), B_PAWN(9), B_KNIGHT(
        10
    ),
    B_BISHOP(11), B_ROOK(12), B_QUEEN(13), B_KING(14),

    //TODO remove vincent sagt ist nur in c und c++ relevant
    PIECE_NB(16);

    companion object {
        fun getPiece(char: Char): Piece {
            return when (char) {
                'P' -> W_PAWN
                'N' -> W_KNIGHT
                'B' -> W_BISHOP
                'R' -> W_ROOK
                'Q' -> W_QUEEN
                'K' -> W_KING
                'p' -> B_PAWN
                'n' -> B_KNIGHT
                'b' -> B_BISHOP
                'r' -> B_ROOK
                'q' -> B_QUEEN
                'k' -> B_KING
                else -> NO_PIECE
            }
        }

        fun getPiece(i: Int): Piece {
            return when (i) {
                1 -> W_PAWN
                2 -> W_KNIGHT
                3 -> W_BISHOP
                4 -> W_ROOK
                5 -> W_QUEEN
                6 -> W_KING
                9 -> B_PAWN
                10 -> B_KNIGHT
                11 -> B_BISHOP
                12 -> B_ROOK
                13 -> B_QUEEN
                14 -> B_KING
                else -> NO_PIECE
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            W_PAWN -> "P"
            W_KNIGHT -> "N"
            W_BISHOP -> "B"
            W_ROOK -> "R"
            W_QUEEN -> "Q"
            W_KING -> "K"
            B_PAWN -> "p"
            B_KNIGHT -> "n"
            B_BISHOP -> "b"
            B_ROOK -> "r"
            B_QUEEN -> "q"
            B_KING -> "k"
            else -> "-"
        }
    }
}

data class Coord(val x: Int, val y: Int)

val BOARD_SIZE: Int = 8

fun Square(x: Int, y: Int): Int {
    return Square.values()[x * BOARD_SIZE + y].value

}

/**
 * A square on a chess board.
 * @property value The value of the square.
 * @constructor Creates a new square.
 */
enum class Square(i: Int = -1) {
    SQ_A1, SQ_B1, SQ_C1, SQ_D1, SQ_E1, SQ_F1, SQ_G1, SQ_H1,
    SQ_A2, SQ_B2, SQ_C2, SQ_D2, SQ_E2, SQ_F2, SQ_G2, SQ_H2,
    SQ_A3, SQ_B3, SQ_C3, SQ_D3, SQ_E3, SQ_F3, SQ_G3, SQ_H3,
    SQ_A4, SQ_B4, SQ_C4, SQ_D4, SQ_E4, SQ_F4, SQ_G4, SQ_H4,
    SQ_A5, SQ_B5, SQ_C5, SQ_D5, SQ_E5, SQ_F5, SQ_G5, SQ_H5,
    SQ_A6, SQ_B6, SQ_C6, SQ_D6, SQ_E6, SQ_F6, SQ_G6, SQ_H6,
    SQ_A7, SQ_B7, SQ_C7, SQ_D7, SQ_E7, SQ_F7, SQ_G7, SQ_H7,
    SQ_A8, SQ_B8, SQ_C8, SQ_D8, SQ_E8, SQ_F8, SQ_G8, SQ_H8,
    SQ_NONE(-2),
    SQUARE_ZERO(0), SQUARE_NB(64);

    var value: Int
    var coordinate: Coord

    init {
        value = if (i == -1) ordinal else i
        coordinate = Coord(value % BOARD_SIZE, value / BOARD_SIZE)
    }

    companion object {
        fun getSquare(coord: Coord): Square {
            return Square.values()[coord.x * BOARD_SIZE + coord.y]
        }

        fun getSquare(i: Int): Square {
            if (isOk(i)) {
                return Square.values()[i]
            }
            return SQ_NONE
        }


    }

    operator fun get(i: Int): Square {
        return Square.values()[i - 1]
    }

    operator fun minus(up: Direction): Square {
        return Square.values()[value - up.value]

    }

    operator fun plus(up: Square): Square {
        return Square.values()[value + up.value]


    }

    operator fun inc(): Square {
        return Square.values()[value + 1]

    }

    init {
        value = if (i == -1) ordinal else i
        coordinate = if (i == -1) Coord(ordinal % BOARD_SIZE, ordinal / BOARD_SIZE)
        else Coord(-1, -1)
    }
}

fun CastlingRights(c: Color, cr: CastlingRights): CastlingRights {
    //TODO check if this is correct
    return CastlingRights.values()[c.value * 4 + cr.value]
}


enum class Direction(val value: Int) {
    NORTH(8), EAST(1), SOUTH(-NORTH.value), WEST(-EAST.value),

    NORTH_EAST(NORTH.value + EAST.value), SOUTH_EAST(SOUTH.value + EAST.value), SOUTH_WEST(SOUTH.value + WEST.value), NORTH_WEST(
        NORTH.value + WEST.value
    );

    operator fun plus(north: Direction): Direction {
        return Direction.values()[value + north.value]

    }

    operator fun minus(up: Direction): Direction {
        return Direction.values()[value - up.value]
    }
}


enum class File(val char: Char) {

    FILE_A('A'), FILE_B('B'), FILE_C('C'), FILE_D('D'), FILE_E('E'), FILE_F('F'), FILE_G('G'), FILE_H(
        'H'
    );


    //FILE_NB('?');
    var value: Int = ordinal


}

enum class Rank(val char: Char) {
    RANK_1('1'), RANK_2('2'), RANK_3('3'), RANK_4('4'), RANK_5('5'), RANK_6('6'), RANK_7('7'), RANK_8(
        '8'
    );


    //RANK_NB('?');

    var value: Int = ordinal


}

enum class Score(value: Int) {
    SCORE_ZERO(0);

    operator fun get(value: Int): Any {
        return Score.values()[value]

    }


}
// constexpr Score makeScore(int mg, int eg) { return Score((mg << 16) + eg); }

fun makeScore(mg: Int, eg: Int): Score {
    return Score.values()[(eg shl 16) + mg]
}

// Additional operators to add a Direction to a Square
fun operatorPlus(s: Square, d: Direction): Square {
    return Square.getSquare(s.ordinal + d.value)
}

fun operatorMinus(s: Square, d: Direction): Square {
    return Square.getSquare(s.ordinal - d.value)
}

fun makeSquare(f: Int, r: Int): Square {
    return Square.getSquare((r shl 3) + f)
}

fun isOk(s: Square): Boolean {
    return s in SQ_A1..SQ_H8
}

fun isOk(s: Int): Boolean {
    return s in 0..63
}

fun fileOf(s: Square): File {

    return File.values()[s.value % 8]
}

fun rankOf(s: Square): Rank {
    return Rank.values()[s.value shr 3]

}

fun relativeRank(c: Color, s: Square): Rank {
    return Rank.values()[s.value xor (c.value * 56)]
}

fun relativeRank(c: Color, r: Rank): Rank {
    return Rank.values()[r.ordinal xor (c.value * 56)]
}

fun pawnPush(c: Color): Direction {
    return if (c == Color.WHITE) Direction.NORTH else Direction.SOUTH
}


fun makePiece(c: Color, pt: PieceType): Piece {
    // a white pawn should be 1
    // a black pawn should be 9
    //bit shift left 3 times
    return Piece.getPiece((c.value shl 3) + pt.value)


}
fun makeSquare(file: File, rank: Rank): Square {
    return Square.getSquare(file.ordinal + rank.ordinal * BOARD_SIZE)

}

fun typeOf(pc: Piece): PieceType {
    return PieceType(pc.value and 7)
}

fun promotionType(m: Move): PieceType {
    TODO("Not yet implemented")
}
fun relativeSquare(color: Color, square: Square): Square {
    //TODO: check if this is correct
    return Square.getSquare(square.value xor (color.value * 56))
}

fun fromSq(m: Move): Square {
    return Square.getSquare((m.value shr 6) and 0x3F)
}

fun toSq(m: Move): Square {
    return Square.getSquare(m.value and 0x3F)
}

fun typeOf(m: Move): MoveType {

    return MoveType.values()[m.value and (3 shl 14)]

}

fun colorOf(pc: Piece): Color {
    assert(pc != Piece.NO_PIECE)
    return Color.values()[pc.value shr 3]

}

fun makeMove(from: Square, to: Square): Move {
    return Move((from.value shl 6) + to.value)

}


fun isOk(m: Move): Boolean {
    return fromSq(m) != toSq(m)

}






