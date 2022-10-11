package chessnet

import chessnet.Square.*


// this File hold all type definitions
typealias Bitboard = ULong
typealias Key = ULong

enum class Move(val value: Int = 0) {
    MOVE_NONE,
    MOVE_NULL(65),
}

enum class MoveType(val value: Int) {
    NORMAL(0),
    PROMOTION(1 shl 14),
    EN_PASSANT(2 shl 14),
    CASTLING(3 shl 14)
}

enum class _Color(val value: Int) {
    WHITE(0),
    BLACK(1),
    NONE_NB(2)
}

data class Color(val value: Int) {
    companion object {
        val WHITE = Color(0)
        val BLACK = Color(1)
        val COLOR_NB = Color(2)
    }
}

enum class CastlingRights(val value: Int) {
    NO_CASTLING(0),
    WHITE_OO(1),
    WHITE_OOO(WHITE_OO.value shl 1),
    BLACK_OO(WHITE_OO.value shl 2),
    BLACK_OOO(WHITE_OO.value shl 3),

    KING_SIDE(WHITE_OO.value or BLACK_OO.value),
    QUEEN_SIDE(WHITE_OOO.value or BLACK_OOO.value),
    WHITE_CASTLING(WHITE_OO.value or WHITE_OOO.value),
    BLACK_CASTLING(BLACK_OO.value or BLACK_OOO.value),
    ANY_CASTLING(WHITE_CASTLING.value or BLACK_CASTLING.value),

    CASTLING_RIGHT_NB(16)

}

enum class Value(val value: Int) {
    VALUE_ZERO(0),
    VALUE_DRAW(0),
    VALUE_KNOWN_WIN(10000),
    VALUE_MATE(32000),
    VALUE_INFINITE(32001),
    VALUE_NONE(32002),

//    VALUE_TB_WIN_IN_MAX_PLY
}

enum class PieceType(val char: Char, val i: Int = 0) {
    NO_PIECE_TYPE('-', 0),
    PAWN('p', 1),
    KNIGHT('n', 2),
    BISHOP('b', 3),
    ROOK('r', 4),
    QUEEN('q', 5),
    KING('k', 6),
    ALL_PIECES('?', 0),
    PIECE_TYPE_NB('#', 8);

    val value: Int = if (i == -1) ordinal else i


    override fun toString(): String {
        return char.toString();
    }
}


//NO_PIECE,
//  W_PAWN = PAWN,     W_KNIGHT, W_BISHOP, W_ROOK, W_QUEEN, W_KING,
//  B_PAWN = PAWN + 8, B_KNIGHT, B_BISHOP, B_ROOK, B_QUEEN, B_KING,
//  PIECE_NB = 16
enum class Piece(val value: Int = 0) {
    NO_PIECE(0),
    W_PAWN(1),
    W_KNIGHT(2),
    W_BISHOP(3),
    W_ROOK(4),
    W_QUEEN(5),
    W_KING(6),
    B_PAWN(9),
    B_KNIGHT(10),
    B_BISHOP(11),
    B_ROOK(12),
    B_QUEEN(13),
    B_KING(14),

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
    SQUARE_ZERO(0),
    SQUARE_NB(64);


    companion object {
        fun getSquare(coord: Coord): Square {
            return squareArray[coord.x][coord.y]
        }

        // getSquare(int: Int): Square
        fun getSquare(i: Int): Square {
            return values()[i]
        }


        val squareArray: Array<Array<Square>> = Array(BOARD_SIZE) {
            Array(BOARD_SIZE) {
                SQ_NONE
            }

        }

        /*init {
            for (i in 0..7) {
                for (j in 0..7) {
                    squareArray[i][j] = getSquare(i * 8 + j)

                }
            }

        }*/

    }

    val coordinate: Coord
    val value: Int

    init {
        value = if (i == -1) ordinal else i

        coordinate = if (i == -1)
            Coord(ordinal % BOARD_SIZE, ordinal / BOARD_SIZE)
        else
            Coord(-1, -1)
    }
}

enum class Direction(val value: Int) {
    NORTH(8),
    EAST(1),
    SOUTH(-NORTH.value),
    WEST(-EAST.value),

    NORTH_EAST(NORTH.value + EAST.value),
    SOUTH_EAST(SOUTH.value + EAST.value),
    SOUTH_WEST(SOUTH.value + WEST.value),
    NORTH_WEST(NORTH.value + WEST.value)
}


fun isOk(s: Square): Boolean {
    return s >= SQ_A1 && s <= SQ_H8;
}

fun fileOf(s: Square): Any {

    return File.values()[s.value % 8]
}

fun rankOf(s: Square): Any {
    return Rank.values()[s.value shr 3];

}

enum class File(val char: Char) {
    FILE_A('A'),
    FILE_B('B'),
    FILE_C('C'),
    FILE_D('D'),
    FILE_E('E'),
    FILE_F('F'),
    FILE_G('G'),
    FILE_H('H');
    //FILE_NB('?');

    override fun toString(): String {
        return char.toString()
    }
}

enum class Rank(val char: Char) {
    RANK_1('1'),
    RANK_2('2'),
    RANK_3('3'),
    RANK_4('4'),
    RANK_5('5'),
    RANK_6('6'),
    RANK_7('7'),
    RANK_8('8');
    //RANK_NB('?');

    override fun toString(): String {
        return char.toString()
    }
}


fun makePiece(c: Color, pt: PieceType): Piece {
    // a white pawn should be 1
    // a black pawn should be 9
    //bit shift left 3 times
    return Piece.getPiece((c.value shl 3) + pt.value)


}

fun relativeSquare(color: Color, square: Square): Square {
    return Square.getSquare(
        Coord(
            square.coordinate.x,
            if (color == Color.WHITE) square.coordinate.y else 7 - square.coordinate.y
        )
    )
}

