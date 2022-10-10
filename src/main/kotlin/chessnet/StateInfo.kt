package chessnet

class StateInfo {

    var pawnKey: Key = 0UL
    var materialKey: Key = 0UL
    //val nonPawnMaterial: Value =
    val castlingRights: Int = 0
    var rule50: Int = 0
    val pliesFromNull: Int = 0
    var epSquare: Square = Square.SQ_A1

    var key: Key = 0UL
    var checkersBB: Bitboard = 0UL
    val previous: StateInfo? = null


}