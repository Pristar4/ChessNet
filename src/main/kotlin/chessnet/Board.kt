package chessnet

import chessnet.types.GenerateMoves

object Board {
    val boardSize = 64
    var squares = IntArray(boardSize)

    var ColorToMove = 0
    fun init() {
        print(GenerateMoves())
    }

    fun pretty() {
        val pieceSymbols = mapOf(
            0 to ".",
            9 to "K",
            10 to "P",
            11 to "N",
            12 to "B",
            13 to "R",
            14 to "Q",
            17 to "k",
            18 to "p",
            19 to "n",
            20 to "b",
            21 to "r",
            22 to "q"
        )

        /*for (i in squares.indices) {
            //align board
            if (i % 8 == 0) {
                println()
            }
            //if "i" is only 1 letter and not 0
            if (squares[i] < 10 && squares[i] != 0) {
                print(" ")
            }

            if (squares[i] == 0) {
                print(" 0 ")
            } else {
                print(squares[i].toString() + " ")
            }


        }*/
        for (i in squares.indices) {
            if (i % 8 == 0) {
                println()
            }
            print(pieceSymbols[squares[i]] + " ")
        }
    }
}
