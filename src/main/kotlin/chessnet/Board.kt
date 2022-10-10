package chessnet

import chessnet.Color.Companion.BLACK
import chessnet.Color.Companion.WHITE
import chessnet.PieceType.*

class Board {

    val tiles: List<List<Tile>> = List(BOARD_SIZE) {
        y -> List(BOARD_SIZE) {
            x -> Tile(Coord(BOARD_SIZE - x, y))
        }
    };

    constructor () {
    }

    fun addPiece(type: PieceType, color: Color, square: Square) {
        val coords = square.coordinate;
        var tile = tiles[coords.x][coords.y];
        tile.type = type
        tile.color = color;
    }

    //var fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    fun initWhite () {
        for (p in 0..7) {
            addPiece(PAWN, WHITE, Square.getSquare(Coord(p, 1)))
        }

        addPiece(ROOK, WHITE, Square.getSquare(Coord(0, 0)))
        addPiece(ROOK, WHITE, Square.getSquare(Coord(7, 0)))
        addPiece(KNIGHT, WHITE, Square.getSquare(Coord(1, 0)))
        addPiece(KNIGHT, WHITE, Square.getSquare(Coord(6, 0)))
        addPiece(BISHOP, WHITE, Square.getSquare(Coord(2, 0)))
        addPiece(BISHOP, WHITE, Square.getSquare(Coord(5, 0)))
        addPiece(QUEEN, WHITE, Square.getSquare(Coord(3, 0)))
        addPiece(KING, WHITE, Square.getSquare(Coord(4, 0)))
    }

    fun initBlack () {
        val pawnRow = 6;
        val backRow = 7;
        for (p in 0..7) {
            addPiece(PAWN, BLACK, Square.getSquare(Coord(p, pawnRow)))
        }

        addPiece(ROOK,   BLACK, Square.getSquare(Coord(0, backRow)))
        addPiece(ROOK,   BLACK, Square.getSquare(Coord(7, backRow)))
        addPiece(KNIGHT, BLACK, Square.getSquare(Coord(1, backRow)))
        addPiece(KNIGHT, BLACK, Square.getSquare(Coord(6, backRow)))
        addPiece(BISHOP, BLACK, Square.getSquare(Coord(2, backRow)))
        addPiece(BISHOP, BLACK, Square.getSquare(Coord(5, backRow)))
        addPiece(QUEEN,  BLACK, Square.getSquare(Coord(3, backRow)))
        addPiece(KING,   BLACK, Square.getSquare(Coord(4, backRow)))
    }

    override fun toString(): String {
        var rowOffset = " ";
        var str = " ${rowOffset}A B C D E F G H\n";

        tiles.asReversed().forEach { y ->
            str += "${y[0].coord.y +1}${rowOffset}";
            y.forEach { x -> str += "${x} " }
            str += "\n"
        }

        return str;
    }
}

//SQ_A1, SQ_B1, SQ_C1, SQ_D1, SQ_E1, SQ_F1, SQ_G1, SQ_H1,
//SQ_A2, SQ_B2, SQ_C2, SQ_D2, SQ_E2, SQ_F2, SQ_G2, SQ_H2,
//SQ_A3, SQ_B3, SQ_C3, SQ_D3, SQ_E3, SQ_F3, SQ_G3, SQ_H3,
//SQ_A4, SQ_B4, SQ_C4, SQ_D4, SQ_E4, SQ_F4, SQ_G4, SQ_H4,
//SQ_A5, SQ_B5, SQ_C5, SQ_D5, SQ_E5, SQ_F5, SQ_G5, SQ_H5,
//SQ_A6, SQ_B6, SQ_C6, SQ_D6, SQ_E6, SQ_F6, SQ_G6, SQ_H6,
//SQ_A7, SQ_B7, SQ_C7, SQ_D7, SQ_E7, SQ_F7, SQ_G7, SQ_H7,
//SQ_A8, SQ_B8, SQ_C8, SQ_D8, SQ_E8, SQ_F8, SQ_G8, SQ_H8,

/// A8 B8  C8  D8  E8  F8  G8  H8
/// A7 B7  C7  D7  E7  F7  G7  H7
/// A6 B6  C6  D6  E6  F6  G6  H6
/// A5 B5  C5  D5  E5  F5  G5  H5
/// A4 B4  C4  D4  E4  F4  G4  H4
/// A3 B3  C3  D3  E3  F3  G3  H3
/// A2 B2  C2  D2  E2  F2  G2  H2
/// A1 B1  C1  D1  E1  F1  G1  H1
