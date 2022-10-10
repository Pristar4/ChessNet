package chessnet

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class TypesKtTest {

    @Test
    fun makePiece() {
        val wPawn = makePiece(Color.WHITE, PieceType.PAWN)
        val actual = wPawn.value
        assertEquals(1, actual)

    }
}