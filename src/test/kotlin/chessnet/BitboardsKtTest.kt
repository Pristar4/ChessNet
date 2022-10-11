package chessnet

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BitboardsKtTest {

    @Test
    fun safeDestinationTest(): Unit {

        println("safeDestinationTest")
        val s1 = 0
        val step = 1
        val expResult = 1UL
        val result = safeDestination(Square.getSquare(s1), step)
        println("expResult = ${expResult.toString(2).padStart(64, '0')}")
        println("result    = ${result.toString(2).padStart(64, '0')}")
        assertEquals(expResult, result)

    }
}