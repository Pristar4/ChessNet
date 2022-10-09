package chessnet

import java.util.*

class Uci {

    fun init() {
        TODO()
    }

     companion object {
        const val StartFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"


         fun position(pos:Position, scanner:Scanner,states:List<String>){
             var m:Move
             var token : String
             var fen : String

            // TODO: MAKE A SCANNER ONLY FOR THE LOOP AND USE IT TO READ THE NEXT TOKEN NOT THIS ONE
                token = scanner.next()
             if (token == "startpos") {
                    fen = StartFEN
                 println("fen: $fen")
                 // Consume the "moves" token, if any
                    if (scanner.hasNext()) {
                        token = scanner.next()
                    }
                 }
             else if (token == "fen") {
                      fen = ""
                      while (scanner.hasNext()) {
                            token = scanner.next()
                            if (token == "moves") break
                            fen += "$token "
                      }
                 } else return

             //states = StateListPtr(StateInfo(), 0)
            //pos.set(fen, false, states, pos.thread())


         }
        fun go(pos: Int, scanner: Scanner, states: List<String>) {
            println("bestmove e2e4")
        }

        fun loop(argv: Array<String>) {

            val pos:Position = Position()

            val scanner = Scanner(System.`in`)
            val argc = argv.size
            var token = ""
            var cmd = ""
            var states: StateInfo =  StateInfo()// Drop the old state and create a new one
            pos.set(StartFEN, false, states)


            // Parse the move list, if any
            //while(scanner.hasNext() && (m = UCI::to_move(pos, token)) != MOVE_NONE ){


            for (arg in argv) cmd += "$arg "
            do {
                // Wait for an input or an end-of-file
                cmd = if (scanner.hasNextLine()) {
                    scanner.nextLine()
                } else {
                    "quit"
                }
                // Avoid a stale if getline() returns nothing or1 a blank line

                val token = cmd.split(" ").first()

                if (token == "quit" || token == "stop") //TODO : Stop Threads
                else if (token == "uci") {
                    print("id name " + engineInfo(true) + "\n" + "uciok\n")
                }
                else if (token == "go") {
                    var pos = 0
                    var states = listOf<String>()
                    go(pos, scanner, states)
                }
                else if (token == "position") {
                    position(pos, scanner, listOf<String>())
                }
                else if (token == "ucinewgame") {
                    println("bestmove ")
                } //Search.clear()
                else if (token == "isready") {
                    println("readyok")
                }
                else if (token == "--help" || token == "help" || token == "-license") {
                    print(
                        "\n Chessnet is a uci chess engine written in Kotlin." +
                                "\nIt is based on the Stockfish chess engine." +
                                "\nIt is free and open source software distributed under the" +
                                "\nGNU General Public License version 3." +
                                "\nFor more information visit https://github.com/Pristar4/Chessnet" +
                                "\nor read the README file.\n"
                    )
                }
                else if (token.isNotEmpty() && token[0] != '#') {
                    println("Unknown command: $token")
                }


            } while (token != "quit" && argc == 0) //The command-line arguments are one-shot


        }
    }

}
