package chessnet

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class PositionTest {

    @Test
    fun square() {
        val square = Square.getSquare(Coord(0, 0))
        assertEquals("a1", square.toString())
    }
}