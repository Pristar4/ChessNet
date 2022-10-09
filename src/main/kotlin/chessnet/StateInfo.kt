package chessnet

class StateInfo {

    val pawnKey: Key = 0UL
    val materialKey: Key = 0UL
    //val nonPawnMaterial: Value =
    val castlingRights: Int = 0
    val rule50: Int = 0
    val pliesFromNull: Int = 0
    val epSquare: Square = Square.SQ_A1

    val key: Key = 0UL
    val checkersBB: Bitboard = 0UL
    val previous: StateInfo? = null


}