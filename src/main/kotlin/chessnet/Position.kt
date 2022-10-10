package chessnet

import chessnet.PieceType.*
import chessnet.Piece.*
import chessnet.Color.*
import chessnet.Color.Companion.COLOR_NB
import chessnet.CastlingRights.*
import chessnet.Square.*
import java.util.*
import kotlin.math.max


class Position {


    // Data members
    var board: Array<Piece> = Array(SQUARE_NB.value) { NO_PIECE }
    var byTypeBB: Array<Bitboard> = Array(PIECE_TYPE_NB.value) { 0UL }
    var byColorBB: Array<Bitboard> = Array(COLOR_NB.value) { 0UL }
    var pieceCount: Array<Int> = Array(PIECE_NB.value) { 0 }
    var castlingRightsMask: Array<Int> = Array(SQUARE_NB.value) { 0 }
    var castlingRookSquare: Array<Square> =
        Array(CASTLING_RIGHT_NB.value) { SQ_A1 }
    var castlingPath: Array<Bitboard> = Array(CASTLING_RIGHT_NB.value) { 0UL }
    var thisThread: Thread? = null
    private var st: StateInfo = StateInfo()
    var gamePly: Int = 0
    var sideToMove: Color = Color.WHITE

    //    var psq: Score
    var chess960: Boolean = false

    /*  A list to keep track of the position states along the setup moves( from the start position to the position
     *  just before the search starts). Needed by 'draw by repetition' detection.
     *  use deque because we need to be able to remove the last element
     */
    public var StateList: Deque<StateInfo> = ArrayDeque<StateInfo>()

    /*  Position::set() initializes the position object with the given FEN string.
     *  This function is not very robust - make sure that input FENs are correct,
     *  this is assumed to be the responsibility of the GUI.
     */
    fun set(fenStr: String, isChess960: Boolean, si: StateInfo):Position {/*
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
                putPiece(Piece.getPiece(token), sq)
                col++
            }
        }


        // 2. Active color
        token = ss.next().single()  // Consume " "
        token = ss.next().single() // Consume "w" or "b"
        sideToMove = if (token == 'w') Color.WHITE else Color.BLACK
        token = ss.next().single() // Consume "w" or "b"

        /* 3. Castling availability. Compatible with 3 standards: Normal FEN standard,
         Shredder-FEN that uses the letters of the columns on which the rooks began
         the game instead of KQkq and also X-FEN standard that, in case of Chess960,
         if an inner rook is associated with the castling right, the castling tag is
         replaced by the file letter of the involved rook, as for the Shredder-FEN.*/
        while (ss.hasNext() && !ss.hasNext("\\s")) {
            var rsq: Square = SQ_NONE
            val c: Color = if (token.isLowerCase()) Color.BLACK else Color.WHITE
            var rook: Piece = makePiece(c, PieceType.ROOK)
            token = ss.next().single().uppercaseChar()

            //FIXME: Castling availability is broken
            /*
            if (token == 'K'){
                // for (rsq = relative_square(c, SQ_H1); piece_on(rsq) != rook; rsq = Square(rsq.value - 1)) {}
                rsq = relativeSquare(c, SQ_H1)

                while (pieceOn(rsq) != rook) {

                    var coord:Coord = Coord(rsq.value, rsq.value - 1)
                    println("rsq = $rsq rook = $rook pieceOn(rsq) = ${pieceOn(rsq)} coord = $coord")
                    rsq = Square.getSquare(coord)
                }
            }
            */


        }

        /* 4. En passant square.
         * Ignore if square is invalid or  not  on the side to move relative rank 6.
         */
        var enpassant: Boolean = false
        //TODO: En passant square implementation

        if (!enpassant)
            st.epSquare = SQ_NONE

        // 5. Halfmove clock and 6. Fullmove number
        ss.useDelimiter("\\s+")
        st.rule50 = gamePly
        //TODO: check if rule50 number is correct

        /*
         * Convert from fullmove starting from 1 to gamePly starting from 0,
         * handle also common incorrect FEN with fullmove = 0.
         */
        var gamePlyRight = (if (sideToMove == Color.BLACK) 1 else 0)

        gamePly = max(
            2 * (gamePly - 1),
            0
        ) + gamePlyRight
        //TODO: check if this gamePly number is correct

        chess960 = isChess960
        thisThread = Thread.currentThread()
        //thisThread = th
        set_state(st)

        assert(posIsOk())

        return this


    }



    private fun setCheckInfo(si: StateInfo) {
        //TODO: setCheckInfo implementation


    }

    private fun set_state(si: StateInfo) {
        si.key = 0u
        si.materialKey = 0u
//        si.pawnKey = Zobrist.noPawns
//        si.nonPawnMaterial[Color.WHITE] = 0
//        si.nonPawnMaterial[Color.BLACK] = 0
        si.checkersBB = 0u

//        setCheckInfo(si)
        //TODO: set_state implementation

        /*        for (Bitboard b = pieces(); b; )
                {
                    Square s = pop_lsb(b);
                    Piece pc = piece_on(s);
                    si->key ^= Zobrist::psq[pc][s];

                    if (type_of(pc) == PAWN)
                        si->pawnKey ^= Zobrist::psq[pc][s];

                    else if (type_of(pc) != KING)
                    si->nonPawnMaterial[color_of(pc)] += PieceValue[MG][pc];
                }

                if (si->epSquare != SQ_NONE)
                si->key ^= Zobrist::enpassant[file_of(si->epSquare)];

                if (sideToMove == BLACK)
                    si->key ^= Zobrist::side;

                si->key ^= Zobrist::castling[si->castlingRights];

                for (Piece pc : Pieces)
                for (int cnt = 0; cnt < pieceCount[pc]; ++cnt)
                si->materialKey ^= Zobrist::psq[pc][cnt];*/







    }


    private fun pieceOn(rsq: Square): Any {
        assert(isOk(rsq))
        return board[rsq.value]
    }


    private fun makeSquare(file: File, rank: Rank): Square {
        return Square.values()[file.ordinal + rank.ordinal * BOARD_SIZE]
    }

    private fun putPiece(piece: Piece, sq: Square) {
        board[sq.ordinal] = piece
    }

    private fun posIsOk(): Boolean {
        //TODO: posIsOk implementation
        return true
    }


}



