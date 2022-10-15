package chessnet

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BitboardsKtTest {

    data class TestBitboard(var bb: Bitboard)

    @Test
    fun popLsb() {
        var testBitboard = TestBitboard(0x0000000000000001UL)

        var square = popLsb(testBitboard.bb)
        //TODO: the popLsb function does not change the value of the bitboard

        var actual = testBitboard.bb
        var expected = 0x0000000000000000UL
        var expectedSquare = Square.SQ_A1
        assertEquals(expected, actual)
        assertEquals(expectedSquare, square)

    }
}