package chessnet

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class PositionTest {

    @Test
    fun setPositionConstructor() {
        Bitboards.init()
        val states = ArrayDeque<StateInfo>()
        val expected:Bitboard = 18446462598732906495UL
        val pos = Position()
        states.add(StateInfo())
        pos.set(Uci.StartFEN, false, states.last())

        assertEquals(expected, pos.pieces())


    }

    @Test
    fun getByTypeBB() {
        Bitboards.init()
        val states = ArrayDeque<StateInfo>()
        states.add(StateInfo())
        // create a position
        val pos = Position()
        // init Position with the StartFEN
        pos.set(Uci.StartFEN, false, states.last())

        // get the bitboard for the white rooks
        val bb = pos.byColorBB[Color.WHITE.value] and pos.byTypeBB[PieceType.ROOK.value]
        println(pos.byColorBB[Color.WHITE.value])
        println(Bitboards.pretty(pos.byColorBB[Color.WHITE.value]))
        // check if the bitboard is correct
//        assertEquals( 0x8100000000000081UL,bb)
    }

    @Test
    fun putPieceOnPosition() {



    }


}