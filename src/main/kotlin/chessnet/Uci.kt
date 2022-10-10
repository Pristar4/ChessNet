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
            states: ArrayDeque<StateInfo>
        ) {
            var m: Move
            var token: String = ""
            var fen: String


            token = scanner.next()

            println("token: $token")

            if (token == "startpos") {
                fen = StartFEN
                println("startfen")
                if (scanner.hasNext()) {
                    scanner.reset() // Consume the "moves" token, if any
                }


            } else if (token == "fen") {
                fen = ""
                println("FEN: WORKING")
                while (scanner.hasNext()) {
                    token = scanner.next()
                    if (token == "moves") break
                    fen += "$token "
                }
            } else return


            states.clear()  // Drop the old state and create a new one
            //FIXME: is this correct? adding a new state here?
            states.add(StateInfo())
            pos.set(fen, false, states.last)

            // Parse move list (if any)
            while (scanner.hasNext()) {
                token = scanner.next()
                m = Uci.to_move(pos, token)
                if (m == Move.MOVE_NONE) break

            }

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
            var states: ArrayDeque<StateInfo> = ArrayDeque<StateInfo>()
            //FIXME: is this correct? add a state to the deque here?
            states.add(StateInfo())
            // Drop the old state and create a new one
            pos.set(StartFEN, false, states.last)


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

                if (ss.hasNext()) token = ss.next()

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
                    position(pos, ss, states)
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

        /* to_move() converts a given string representing a move in coordinate
         * notation ( g1f3,a7a8q) to the corresponding legal Move, if any.
         */
        private fun to_move(pos: Position, _str: String): Move {
            var str = _str
            if (str.length == 5) {
                //The promotion piece character must be lower case
                str = str.substring(0, 4) + str[4].lowercaseChar()
            }
            // FIXME:  Add MoveLists/ Add Move Generation
            //for (const auto& m : MoveList<LEGAL>(pos))
            //      if (str == UCI::move(m, pos.is_chess960()))
            //          return m;

            return Move.MOVE_NONE


        }

    }

}
