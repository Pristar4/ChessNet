package chessnet

import chessnet.movegen.Movegen
import java.util.*

class Uci {

    fun init() {
        TODO()
    }

    companion object {
        const val StartFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"


        private fun position(
            pos: Position, scanner: Scanner, states: ArrayDeque<StateInfo>
        ) {
            var m: Move
            var token: String = ""
            var fen: String


            token = scanner.next()

//            println("token: $token")

            if (token == "startpos") {
                fen = StartFEN
                if (scanner.hasNext()) {
                    token = scanner.next() // Consume the "moves" token, if any
                }


            } else if (token == "fen") {
                fen = ""
                while (scanner.hasNext()) {
                    token = scanner.next()
                    if (token == "moves") break
                    fen += "$token "
                }
            } else return


           /* states.clear() */ // Drop the old state and create a new one
            //FIXME: is this correct? adding a new state here?
            states.add(StateInfo())
            pos.set(fen, false, states.last)

            // Parse move list (if any)
            while (scanner.hasNext()) {
                token = scanner.next()
                m = Uci.toMove(pos, token)
                if (m == Move.MOVE_NONE) break
                states.add(StateInfo())
                pos.doMove(m, states.last)

            }

        }


        fun go(pos: Position, scanner: Scanner, states: ArrayDeque<StateInfo>) {


            var token: String
            var pondermode: Boolean = false

            while (scanner.hasNext()) {
                token = scanner.next()
                if (token == "searchmoves") // Needs to be the Last command on the line
                {
                    while (scanner.hasNext()) {
                        token = scanner.next()
                        //Todo add move to searchmoves
                    }
                } else if (token == "infinite") {
                }

            }
//            Threads.startThinking(pos,states,limits,ponderMode)
            //get valid moves
            var moves = Movegen.generate<Movegen.GenType>(pos, listOf())
//            println("moves: $moves")
            println("bestmove e2e4")


        }

        fun loop(argv: Array<String>) {

            val pos: Position = Position()

            val scanner = Scanner(System.`in`)
            val argc = argv.size
            var token = ""
            var cmd = ""
            val states: ArrayDeque<StateInfo> = ArrayDeque<StateInfo>()
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
                // Avoid a stale if getline() returns nothing or1 a blank line
                token = ""
                val ss = Scanner(cmd)

                if (ss.hasNext()) token = ss.next()

//                println("token: $token")


                if (token == "quit" || token == "stop") //TODO : Stop Threads
                else if (token == "uci") {
                    print("id name " + engineInfo(true) + "\n" + "uciok\n")
                } else if (token == "go") {
                    go(pos, ss, states)
                } else if (token == "setoption") {
                } else if (token == "position") {
                    position(pos, ss, states)
                } else if (token == "ucinewgame") {
                    //Search::clear(); // After a new game our old search is not valid
                } //Search.clear()
                else if (token == "isready") {
                    println("readyok")
                } else if (token == "--help" || token == "help" || token == "-license") {
                    print(
                        "\n Chessnet is a uci chess engine written in Kotlin." + "\nIt is based on the Stockfish chess engine." + "\nIt is free and open source software distributed under the" + "\nGNU General Public License version 3." + "\nFor more information visit https://github.com/Pristar4/Chessnet" + "\nor read the README file.\n"
                    )

                } else if (token == "d") println(Bitboards.pretty(DARK_SQUARES))
                else if (token.isNotEmpty() && token[0] != '#') {
                    println("Unknown command: $token")
                }


            } while (token != "quit" && argc == 0) //FIXME: The command-line arguments  should be one-shot


        }

        /* move() converts a Move to a string in coordinate notation (g1f3, a7a8q).
         * The only special case is castling where the e1g1 notation is printed in
         * standard chess mode and in e1h1 notation it is printed in Chess960 mode.
         * Internally, all castling moves are always encoded as 'king captures rook'.
         */
        fun move(m: Move, chess960: Boolean) {         /*   var from: Square = fromSquare(m)
                        var to: Square = toSquare(m)

                        if (m == MOVE_NONE) return "(none)"
                        if (m == MOVE_NULL) return "0000"
                        if (typeOf<Move>(m) == CASTLING && !chess960) to =
                            makeSquare(if (to > from) to FILE_G else to FILE_C, to RANK_1)*/
        }


        /* toMove() converts a given string representing a move in coordinate
         * notation ( g1f3,a7a8q) to the corresponding legal Move, if any.
         */
        private fun toMove(pos: Position, _str: String): Move {
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
