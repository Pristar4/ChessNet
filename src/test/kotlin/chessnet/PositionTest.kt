package chessnet

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class PositionTest {

    @Test
    fun setPositionConstructor() {
        val states = ArrayDeque<StateInfo>()
        states.add(StateInfo())
        // create a position
        val pos = Position()
        // init Position with the StartFEN
        pos.set(Uci.StartFEN, false, states.last())



    }
}