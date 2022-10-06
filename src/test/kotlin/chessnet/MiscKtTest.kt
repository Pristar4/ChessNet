package chessnet

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions as Assertions

internal class MiscKtTest {

    @Test
    fun engineInfo() {
        //  get current month day year
        val date = java.time.LocalDate.now()
        val info: String = chessnet.engineInfo()
        val version: String = chessnet.ENGINE_VERSION
        val expected = "ChessNet v$version  $date by Felix Jung"
        Assertions.assertEquals(expected, info)
    }
}
