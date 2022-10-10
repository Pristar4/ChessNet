package chessnet

import java.util.*

class Uci {

    fun init() {
        TODO()
    }

    companion object {
        const val StartFEN =
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"


        private fun position(
            pos: Position,
            scanner: Scanner,
            states: List<String>
        ) {
            var m: Move
            var token: String = ""
            var fen: String

            token = scanner.next()
            println("token: $token")

            if (token == "startpos") {
                fen = StartFEN
                // Check if there is a next token but dont wait for it
                println("startfen")
                if (scanner.hasNext()) {
                    scanner.reset()
                }


            } else if (token == "fen") {
                fen = ""
                while (scanner.hasNext()) {
                    token = scanner.next()
                    if (token == "moves") break
                    fen += "$token "
                }
            } else {
                println("Error (unknown command): $token")


            }

            //states = StateListPtr(StateInfo(), 0)
            //pos.set(fen, false, states, pos.thread())


        }

        fun go(pos: Int, scanner: Scanner, states: List<String>) {
            println("bestmove e2e4")
        }

        fun loop(argv: Array<String>) {

            val pos: Position = Position()

            val scanner = Scanner(System.`in`)
            val argc = argv.size
            var token = ""
            var cmd = ""
            var states: StateInfo =
                StateInfo()// Drop the old state and create a new one
            pos.set(StartFEN, false, states)


            // Parse the move list, if any
            //while(scanner.hasNext() && (m = UCI::to_move(pos, token)) != MOVE_NONE ){


            for (arg in argv) cmd += "$arg "
            do {
                if (argc == 0 && !scanner.hasNextLine()) {
                    cmd = "quit"
                }

                if (scanner.hasNextLine()) {
                    scanner.useDelimiter(" ")
                    cmd = scanner.nextLine()
                    val ss = Scanner(cmd)
                } else {

                    cmd = "quit"
                }
                val ss = Scanner(cmd)

                if (ss.hasNext())token = ss.next()

                println("token: $token")


                // Avoid a stale if getline() returns nothing or1 a blank line


                if (token == "quit" || token == "stop") //TODO : Stop Threads
                else if (token == "uci") {
                    print("id name " + engineInfo(true) + "\n" + "uciok\n")
                } else if (token == "go") {
                    var pos = 0
                    var states = listOf<String>()
                    go(pos, ss, states)
                } else if (token == "position") {
                    position(pos, ss, listOf<String>())
                } else if (token == "ucinewgame") {
                    //Search::clear(); // After a new game our old search is not valid
                } //Search.clear()
                else if (token == "isready") {
                    println("readyok")
                } else if (token == "--help" || token == "help" || token == "-license") {
                    print(
                        "\n Chessnet is a uci chess engine written in Kotlin." +
                                "\nIt is based on the Stockfish chess engine." +
                                "\nIt is free and open source software distributed under the" +
                                "\nGNU General Public License version 3." +
                                "\nFor more information visit https://github.com/Pristar4/Chessnet" +
                                "\nor read the README file.\n"
                    )
                } else if (token.isNotEmpty() && token[0] != '#') {
                    println("Unknown command: $token")
                }


            } while (token != "quit" && argc == 0) //FIXME: The command-line arguments  should be one-shot


        }
    }

}
