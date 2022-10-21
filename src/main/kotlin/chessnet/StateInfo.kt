package chessnet

class StateInfo {

    var pawnKey: Key = 0UL
    var materialKey: Key = 0UL
    val nonPawnMaterial: Value = Value.VALUE_ZERO
    //val nonPawnMaterial: Value =
    var castlingRights: Int = 0
    var rule50: Int = 0
    var pliesFromNull: Int = 0
    var epSquare: Square = Square.SQ_A1

    //Not copied when making a move(will be recomputed anyhow)
    var key: Key = 0UL
    var checkersBB: Bitboard = 0UL
    var previous: StateInfo? = null
    var blockersForKing: Array<Bitboard> = Array(Color.COLOR_NB.value) { 0UL }
    var pinnersForKing: Array<Bitboard> = Array(Color.COLOR_NB.value) { 0UL }
    var checkSquares: Array<Bitboard> = Array(PieceType.PIECE_TYPE_NB.value) { 0UL }
    var repitition: Int = 0


}