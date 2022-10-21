package chessnet

//import chessnet.Movegen.*


import chessnet.search.LimitsType
import chessnet.thread.Thread
import java.util.*

class Uci {

    fun init() {
        TODO()
    }

    companion object {
        const val StartFEN =
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"


        private fun position(
            pos: Position, scanner: Scanner, states: ArrayDeque<StateInfo>,
        ) {
            var m: Move
            var token: String = ""
            var fen: String
            token = scanner.next()

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


            //FIXME: is this correct? adding a new state here?
            states.clear()/* states.clear() */
            states.add(StateInfo())
            pos.set(fen, false, states.last)
            states.clear()/* states.clear() */

            // Parse move list (if any)
            while (scanner.hasNext()) {
                token = scanner.next()
                m = toMove(pos, token)
                if (m == Move.MOVE_NONE) break
                states.add(StateInfo())
                pos.doMove(m, states.last)

            }

        }


        private fun go(pos: Position, scanner: Scanner, states: ArrayDeque<StateInfo>) {


            var token: String
            val ponderMode: Boolean = false

            while (scanner.hasNext()) {
                token = scanner.next()
                if (token == "searchmoves") // Needs to be the Last command on the line
                {
                    while (scanner.hasNext()) {
                        token = scanner.next()
                        //Todo add move to searchmoves
                    }
                }


            }
            val limits = LimitsType()
            Thread.startThinking(pos, states, limits, ponderMode)
        }

        fun loop(argv: Array<String>) {

            val pos = Position()

            val scanner = Scanner(System.`in`)
            val argc = argv.size
            var token: String
            var cmd = ""
            val states: ArrayDeque<StateInfo> = ArrayDeque<StateInfo>()
//            states.add(StateInfo())
            //FIXME: is this correct? add a state to the deque here?
            // Drop the old state and create a new one
            states.clear()
            states.add(StateInfo())
            pos.set(StartFEN, false, states.last)


            // Parse the move list, if any
            //while(scanner.hasNext() && (m = UCI::to_move(pos, token)) != MOVE_NONE ){


            for (arg in argv) cmd += "$arg "
            do {
                if (argc == 0 && !scanner.hasNextLine()) {
                    cmd = "quit"
                } else if (scanner.hasNextLine()) {
                    scanner.useDelimiter(" ")
                    cmd = scanner.nextLine()
                    Scanner(cmd)
                } else {

                    cmd = "quit"
                }
                // Avoid a stale if getline() returns nothing or1 a blank line
                token = ""
                val ss = Scanner(cmd)

                if (ss.hasNext()) token = ss.next()

//                println("token: $token")


                if (token == "quit" || token == "stop") //TODO : Stop Threads
                else if (token == "uci") print("id name " + engineInfo(true) + "\n" + "uciok\n")
                else if (token == "go") go(pos, ss, states)
//                else if (token == "setoption") TODO("setoption add later")
                else if (token == "position") position(pos, ss, states)
                else if (token == "ucinewgame") //Search::clear(); // After a new game our old search is not valid
                else if (token == "isready") println("readyok")
                else if (token == "--help" || token == "help" || token == "-license") {
                    print(
                        "\n Chessnet is a uci chess engine written in Kotlin." + "\nIt is based on the Stockfish chess engine." + "\nIt is free and open source software distributed under the" + "\nGNU General Public License version 3." + "\nFor more information visit https://github.com/Pristar4/Chessnet" + "\nor read the README file.\n"
                    )

                } else if (token == "d") println(pos)
                else if (token.isNotEmpty() && token[0] != '#') {
                    println("Unknown command: $token")
                }


            } while (token != "quit" && argc == 0) //FIXME: The command-line arguments  should be one-shot


        }

        fun square(s: Square): String {
//            return std::string{ char('a' + file_of(s)), char('1' + rank_of(s)) }; in kotlin
            return "${('a' + fileOf(s).value)}${('1' + rankOf(s).value)}"
        }

        /** move() converts a Move to a string in coordinate notation (g1f3, a7a8q).
         * The only special case is castling where the e1g1 notation is printed in
         * standard chess mode and in e1h1 notation it is printed in Chess960 mode.
         * Internally, all castling moves are always encoded as 'king captures rook'.
         */
        fun move(m: Move, chess960: Boolean): String {
            /*Square from = from_sq(m);
            Square to = to_sq(m);

            if (m == MOVE_NONE)
                return "(none)";

            if (m == MOVE_NULL)
                return "0000";

            if (type_of(m) == CASTLING && !chess960)
                to = make_square(to > from ? FILE_G : FILE_C, rank_of(from));

            string move = UCI::square(from) + UCI::square(to);

            if (type_of(m) == PROMOTION)
                move += " pnbrqk"[promotion_type(m)];

            return move; in kotlin*/
            var from: Square = fromSq(m)
            var to: Square = toSq(m)
            if (m == Move.MOVE_NONE) return "(none)"
            if (m == Move.MOVE_NULL) return "0000"
            if (typeOf(m) == MoveType.CASTLING && !chess960) to =
                makeSquare(if (to > from) File.FILE_G else File.FILE_C, rankOf(from))
            var move = square(from) + square(to)

            if (typeOf(m) == MoveType.PROMOTION) move += " pnbrqk"[promotionType(m).value]
            return move


        }


        /** toMove() converts a given string representing a move in coordinate
         * notation ( g1f3,a7a8q) to the corresponding legal Move, if any.
         */
        private fun toMove(pos: Position, str: String): Move {

            if (str.length == 5) {
                str.replace(str[4], str[4].lowercaseChar())
            }
            for (m in MoveList(pos, GenType.LEGAL)) {
//                println("move: ${move(m, pos.isChess960)}")
                return m
            }
            return Move.MOVE_NONE


        }

    }

}
