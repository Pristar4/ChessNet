package chessnet

import chessnet.Square.*
import chessnet.Piece.*
import java.util.*


class Position {


    private var st: StateInfo = StateInfo()


    // Data members
    var board: Array<Piece> = Array(SQUARE_NB.value) { NO_PIECE }

    var sideToMove: Color = Color.NONE_NB


    /*  A list to keep track of the position states along the setup moves( from the start position to the position
     *  just before the search starts). Needed by 'draw by repetition' detection.
     *  use deque because we need to be able to remove the last element
     */
    public var StateList: Deque<StateInfo> = ArrayDeque<StateInfo>()

    /*  Position::set() initializes the position object with the given FEN string.
     *  This function is not very robust - make sure that input FENs are correct,
     *  this is assumed to be the responsibility of the GUI.
     */
    fun set(fenStr: String, isChess960: Boolean, si: StateInfo) {/*
                      A FEN string defines a particular position using only the ASCII character set.

                      A FEN string contains six fields separated by a space. The fields are:

                      1) Piece placement (from white's perspective). Each rank is described, starting
                         with rank 8 and ending with rank 1. Within each rank, the contents of each
                         square are described from file A through file H. Following the Standard
                         Algebraic Notation (SAN), each piece is identified by a single letter taken
                         from the standard English names. White pieces are designated using upper-case
                         letters ("PNBRQK") whilst Black uses lowercase ("pnbrqk"). Blank squares are
                         noted using digits 1 through 8 (the number of blank squares), and "/"
                         separates ranks.

                      2) Active color. "w" means white moves next, "b" means black.

                      3) Castling availability. If neither side can castle, this is "-". Otherwise,
                         this has one or more letters: "K" (White can castle kingside), "Q" (White
                         can castle queenside), "k" (Black can castle kingside), and/or "q" (Black
                         can castle queenside).

                      4) En passant target square (in algebraic notation). If there's no en passant
                         target square, this is "-". If a pawn has just made a 2-square move, this
                         is the position "behind" the pawn. Following X-FEN standard, this is recorded only
                         if there is a pawn in position to make an en passant capture, and if there really
                         is a pawn that might have advanced two squares.

                      5) Halfmove clock. This is the number of halfmoves since the last pawn advance
                         or capture. This is used to determine if a draw can be claimed under the
                         fifty-move rule.

                      6) Fullmove number. The number of the full move. It starts at 1, and is
                         incremented after Black's move.
                   */
        var token: Char = ' '
        var col: Int = 0
        var row: Int = 0
        var idx: ULong = 0UL
        var sq: Square = SQ_A8
        val ss: Scanner = Scanner(fenStr)
        ss.useDelimiter("")



        // 1. Piece placement
        while (ss.hasNext() && !ss.hasNext("\\s")) {
            token = ss.next().single()
            if (token.isDigit()) {
                col += token.toString()
                    .toInt() // Advance the given number of files

            } else if (token == '/') {
                row++
                col = 0

            } else if (Piece.getPiece(token) != NO_PIECE) {

                sq = makeSquare(File.values()[col], Rank.values()[row])
                //println("sq = $sq col = $col row = $row token = $token idx = $idx")
                putPiece(Piece.getPiece(token), sq)
                col++
            }/*      token = 'A'
            println("startToken: $token")
        while ((ss.hasNext()) && !token.isWhitespace()) {
            println("token: $token")
            token = ss.next().first()
            println("token: $token")

            //check if token is whitespace


            // if token is a digit, skip the corresponding number of files


            //Advance the given number of files


        */
        }


        // 2. Active color
        token = ss.next().single()  // Consume " "
        token = ss.next().single()
        sideToMove = if (token == 'w') Color.WHITE else Color.BLACK

        /* 3. Castling availability. Compatible with 3 standards: Normal FEN standard,
         Shredder-FEN that uses the letters of the columns on which the rooks began
         the game instead of KQkq and also X-FEN standard that, in case of Chess960,
         if an inner rook is associated with the castling right, the castling tag is
         replaced by the file letter of the involved rook, as for the Shredder-FEN.*/
        while (ss.hasNext() && !ss.hasNext("\\s")) {
            var rsq: Square = SQ_NONE
            var c: Color = if (token.isLowerCase()) Color.BLACK else Color.WHITE
            var rook: Piece = makePiece(c, PieceType.ROOK)

        }


        /*println(board.map { ePiece -> ePiece.char.toString() }.reduce { acc, char ->
                        if (char != " ") acc + char; else {
                            if (acc.last().isDigit() && acc.last().code != 8) {
                                val count: Int = acc.last().code + 1
                                var temp = acc.slice(0..acc.length - 2)
                                temp + count.toString()
                            } else {
                                acc + "1"
                            }
                        }
                    }
            )*//*
                //print the board
                print("  ");
                for (j in 0..7) {
                    print("${File.values()[j]} ")
                }
                print('\n')
                for (i in 0..7) {
                    print("${Rank.values()[BOARD_SIZE - (i + 1)]} ")
                    for (j in 0..7) {
                        print("${board[makeSquare(File.values()[j], Rank.values()[i]).value].char} ")
                    }
                    println()
                }*/
    }


    private fun makeSquare(file: File, rank: Rank): Square {
        return Square.values()[file.ordinal + rank.ordinal * BOARD_SIZE]
    }

    private fun putPiece(piece: Piece, sq: Square) {
//        println("piece = $piece sq = $sq")
        board[sq.ordinal] = piece
    }


}



