package chessnet.position

import chessnet.Board
import chessnet.Piece

object Position {
    // dictionary of piece types
    val pieceTypeFromSymbol = mapOf(
        'p' to Piece.PAWN,
        'n' to Piece.KNIGHT,
        'b' to Piece.BISHOP,
        'r' to Piece.ROOK,
        'q' to Piece.QUEEN,
        'k' to Piece.KING
    )

    fun set(fenStr: String) {
        // new String
        val fenBoard: String = fenStr.split(" ")[0]

        var file = 0
        var rank = 7

        // for each char in fenStr
        fenBoard.forEach { c ->

            if (c == '/') {
                file = 0
                rank--
            } else {
                if (c.isDigit()) {
                    file += c.toString().toInt()
                } else {
                    val pieceColor =
                        if (c.isUpperCase()) Piece.WHITE else Piece.BLACK
                    val pieceType = pieceTypeFromSymbol[c.lowercaseChar()]
                    Board.squares[rank * 8 + file] =
                        pieceColor or pieceType!!
                    file++
                }
            }
        }
    }
}
