package chessnet

class Tile(val coord: Coord) {
    var type: PieceType = PieceType.NO_PIECE_TYPE;
    var color: Color? = null;

    override fun toString(): String {
        return if (color == Color.WHITE) type.toString().uppercase() else type.toString()
    }
}