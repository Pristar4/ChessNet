package chessnet

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BitboardsKtTest {
    @Test
    fun popLsb() {
        val testBo = BitboardObject(0x0000000100000000UL)
        val square: Square = popLsb(testBo)
        val expected = 0x0000000000000000UL
        val expectedSquare = Square.SQ_A5
        val actual = testBo.bb
        assertEquals(expected, actual)
        assertEquals(expectedSquare, square)
    }
}