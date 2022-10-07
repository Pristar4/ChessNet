package chessnet.uci

import chessnet.engineInfo
import java.util.*
import java.util.concurrent.TimeUnit

class Uci {
    fun init() {
        TODO()
    }

    companion object {
        fun go(pos: Int, scanner: Scanner, states: List<String>) {
            println("bestmove e2e4")
        }

        fun loop(argv: Array<String>) {
            var scanner = Scanner(System.`in`)
            var argc = argv.size
            var token = ""
            var cmd = ""


            for (arg in argv) cmd += "$arg "
            do {
                // Wait for an input or an end-of-file
                cmd = if (scanner.hasNextLine()) {
                    scanner.nextLine()
                } else {
                    "quit"
                }
                // Avoid a stale if getline() returns nothing or1 a blank line
                token = ""

                val token = cmd.split(" ").first()

                if (token == "quit" || token == "stop") //TODO : Stop Threads
                else if (token == "uci") print(
                    "id name " + engineInfo(true) + "\n" + "uciok\n"
                )
                else if (token == "go") {
                    var pos = 0
                    var states = listOf<String>()
                    go(pos, scanner, states)
                }
//                else if (token == "position") position(pos,is,states)
                else if (token == "ucinewgame") println("bestmove ") //Search.clear()
                else if (token == "isready") println("readyok")
                else if (token == "--help" || token == "help" || token == "-license") print(
                    "\n Chessnet is a uci chess engine written in Kotlin." +
                            "\nIt is based on the Stockfish chess engine." +
                            "\nIt is free and open source software distributed under the" +
                            "\nGNU General Public License version 3." +
                            "\nFor more information visit https://github.com/Pristar4/Chessnet" +
                            "\nor read the README file.\n"
                )
                else if (token.isNotEmpty() && token[0] != '#') {
                    println("Unknown command: $token")
                }


            } while (token != "quit" && argc == 0) //The command-line arguments are one-shot


        }
    }

}
