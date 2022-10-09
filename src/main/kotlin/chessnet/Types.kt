package chessnet


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

enum class Color(val value: Int) {
    WHITE(0),
    BLACK(1),
    NONE_NB(2)
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

enum class PieceType(val i: Int = 0) {
    NO_PIECE_TYPE, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING,
    ALL_PIECES(0),
    PIECE_TYPE_NB(8);

    val value: Int

    init {
        value = if (i == -1) ordinal else i
    }

    override fun toString(): String {
        return char.toString();
    }
}

enum class Piece(val i: Int = 0) {
    NO_PIECE, W_PAWN(PieceType.PAWN.value), W_KNIGHT, W_BISHOP, W_ROOK, W_QUEEN, W_KING,
    B_PAWN(PieceType.PAWN.value+8), B_KNIGHT, B_BISHOP, B_ROOK, B_QUEEN, B_KING,
    PIECE_NB(16);
    private  var _value: Int = i
    init {
        if (i == -1) {
            _value = ordinal
        }
    }
    //getter
    public val value: Int
        get() = _value
    companion object {
        fun getPiece(char: Char): EPiece {
            return EPiece.values().find { ePiece -> ePiece.char == char } ?: NO_PIECE
        }
    }

    override fun toString(): String {
        return char.toString();
    }
}

data class Coord (val x: Int, val y: Int)

val BOARD_SIZE: Int = 8

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

    private  var _value: Int = i
    init {
        if (i == -1) {
            _value = ordinal
        }
    }
    //getter
    public val value: Int
        get() = _value

    companion object {
        fun getSquare(coord: Coord): Square {
            return squareArray[coord.x][coord.y]
        }

        val squareArray: Array<Array<Square>> = Array(BOARD_SIZE) {
            y -> Array(BOARD_SIZE) {
                x -> values().find {
                    square -> square.coordinate == Coord(x,y)
                } ?: SQ_NONE
            }
        }

        fun A (row: Int): Square {
            return squareArray[0][row];
        }
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

enum class File(val char: Char) {
    FILE_A('A'),
    FILE_B('B'),
    FILE_C('C'),
    FILE_D('D'),
    FILE_E('E'),
    FILE_F('F'),
    FILE_G('G'),
    FILE_H('H'),
    FILE_NB('?');

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
    RANK_8('8'),
    RANK_NB('?');

    override fun toString(): String {
        return char.toString()
    }
}



