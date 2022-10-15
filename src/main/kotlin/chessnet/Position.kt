package chessnet

import chessnet.PieceType.*
import chessnet.Piece.*
import chessnet.Color.*
import chessnet.CastlingRights.*
import chessnet.Square.*
import chessnet.MoveType.*
import java.util.*
import kotlin.math.max


private infix fun ULong.xor(s: Square): ULong {
    return this xor (1UL shl s.value)
}

operator fun Any.get(ordinal: Int): Any {
    return PieceType.values()[ordinal]

}

class Position {


    // Data members
    var board: Array<Piece> = Array(SQUARE_NB.value) { NO_PIECE }
    var byTypeBB: Array<Bitboard> = Array(PIECE_TYPE_NB.value) { 0UL }
    var byColorBB: Array<Bitboard> = Array(COLOR_NB.value) { 0UL }
    var pieceCount: Array<Int> = Array(PIECE_NB.value) { 0 }
    var castlingRightsMask: Array<Int> = Array(SQUARE_NB.value) { 0 }
    var castlingRookSquare: Array<Square> = Array(CASTLING_RIGHT_NB.value) { SQ_A1 }
    var castlingPath: Array<Bitboard> = Array(CASTLING_RIGHT_NB.value) { 0UL }
    var thisThread: Thread? = null
    private var st: StateInfo = StateInfo()
    var gamePly: Int = 0
    var sideToMove: Color = WHITE
    var psq: Score = Score.SCORE_ZERO

    //    var psq: Score
    var chess960: Boolean = false

    /**  A list to keep track of the position states along the setup moves( from the start position to the position
     *  just before the search starts). Needed by 'draw by repetition' detection.
     *  use deque because we need to be able to remove the last element
     */
    public var StateList: Deque<StateInfo> = ArrayDeque<StateInfo>()


    // Castling variables


    // returns an ASCII representation of the position
    override fun toString(): String {
        var s = ""
        var f = File.FILE_A.value
        var r = Rank.RANK_8.value
        var piece: Piece
        s += " +---+---+---+---+---+---+---+---+\n"
        while (r >= Rank.RANK_1.value) {
            while (f <= File.FILE_H.value) {
                piece = pieceOn(makeSquare(File.values()[f], Rank.values()[r]))
                s += " | " + if (piece == NO_PIECE) "." else "$piece"
                f++
                s += if (f > File.FILE_H.value) " |" else ""
            }
            s += "\n +---+---+---+---+---+---+---+---+\n"
            f = 0
            r--
        }
        s += "sideToMove: $sideToMove\n"
        s += "castlingRights: ${st.castlingRights}\n"
        s += "epSquare: ${st.epSquare}\n"
        s += "gamePly: $gamePly\n"
        s += "psq: $psq\n"
        return s
    }

    // Position representation
    inline fun movedPiece(m: Move): Piece {
        return pieceOn(fromSq(m))
    }

    fun pieces(pt: PieceType = ALL_PIECES): Bitboard {
        return byTypeBB[pt.value]
    }

    fun pieces(pt1: PieceType, pt2: PieceType): Bitboard {
        return pieces(pt1) or pieces(pt2)
    }

    fun pieces(c: Color): Bitboard {
        return byColorBB[c.value]
    }

    fun pieces(c: Color, pt: PieceType): Bitboard {
        return pieces(c) and pieces(pt)
    }

    fun pieces(c: Color, pt1: PieceType, pt2: PieceType): Bitboard {
        return pieces(c) and (pieces(pt1) or pieces(pt2))
    }

    fun pieces(pt1: PieceType, pt2: PieceType, pt3: PieceType): Bitboard {
        return pieces(pt1) or pieces(pt2) or pieces(pt3)
    }


    fun count(c: Color, pt: PieceType): Int {
        return pieceCount[makePiece(c, pt).value]
    }

    private fun pieceCount(c: Color, pt: PieceType): Int {
        assert(count(c, pt) >= 0)

        return lsb(pieces(c, pt))

    }

    private fun lsb(pieces: Bitboard): Int {
        return pieces.countTrailingZeroBits()
    }


    fun pieceOn(s: Square): Piece {
        assert(isOk(s))
        return board[s.value]
    }

    fun epSquare(): Square {
        return st.epSquare
    }

    fun checkers(): Bitboard {
        return st.checkersBB

    }

    fun blockersForKing(c: Color): Bitboard {

        return st.blockersForKing[c.value]
    }


    private fun checkSquares(pt: PieceType): Bitboard {

        return st.checkSquares[pt.value]


    }

    /**  [set] () initializes the position object with the given FEN string.
     *  This function is not very robust - make sure that input FENs are correct,
     *  this is assumed to be the responsibility of the GUI.
     */
    fun set(fenStr: String, isChess960: Boolean, si: StateInfo): Position {/*
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
                col += token.toString().toInt() // Advance the given number of files

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
        sideToMove = if (token == 'w') WHITE else Color.BLACK
        token = ss.next().single() // Consume "w" or "b"

        /** 3. Castling availability. Compatible with 3 standards: Normal FEN standard,
        Shredder-FEN that uses the letters of the columns on which the rooks began
        the game instead of KQkq and also X-FEN standard that, in case of Chess960,
        if an inner rook is associated with the castling right, the castling tag is
        replaced by the file letter of the involved rook, as for the Shredder-FEN.*/
        while (ss.hasNext() && !ss.hasNext("\\s")) {
            var rsq: Square = SQ_NONE
            val c: Color = if (token.isLowerCase()) Color.BLACK else WHITE
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

        /** 4. En passant square.
         * Ignore if square is invalid or  not  on the side to move relative rank 6.
         */
        var enpassant: Boolean = false
        //TODO: En passant square implementation

        if (!enpassant) st.epSquare = SQ_NONE

        // 5. Halfmove clock and 6. Fullmove number
        ss.useDelimiter("\\s+")
        st.rule50 = gamePly
        //TODO: check if rule50 number is correct

        /**
         * Convert from fullmove starting from 1 to gamePly starting from 0,
         * handle also common incorrect FEN with fullmove = 0.
         */
        var gamePlyRight = (if (sideToMove == Color.BLACK) 1 else 0)

        gamePly = max(
            2 * (gamePly - 1), 0
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


    private fun makeSquare(file: File, rank: Rank): Square {
        return Square.values()[file.ordinal + rank.ordinal * BOARD_SIZE]

    }

    private fun putPiece(piece: Piece, s: Square) {
        board[s.ordinal] = piece
        byTypeBB[ALL_PIECES.value] = byTypeBB[typeOf(piece).value] or squareBb(s)
        byColorBB[colorOf(piece).value] = byColorBB[colorOf(piece).value] or squareBb(s)
        pieceCount[piece.value]++
        pieceCount[makePiece(colorOf(piece), ALL_PIECES).value]++
        //FIXME: psq does not work
//        psq = psq + psq[piece.value][s.ordinal]
    }

    private fun removePiece(s: Square) {
        var pc: Piece = board[s.ordinal]
        byTypeBB[ALL_PIECES.value] = byTypeBB[ALL_PIECES.value] xor s
        byTypeBB[typeOf(pc).value] = byTypeBB[typeOf(pc).value] xor s
        byColorBB[colorOf(pc).value] = byColorBB[colorOf(pc).value] xor s
        board[s.ordinal] = NO_PIECE
        pieceCount[pc.value]--
        pieceCount[makePiece(colorOf(pc), ALL_PIECES).value]--
        // Todo add Score values
//        psq[psq.ordinal] = psq -      psq[pc.value][s.value]
    }

    private fun posIsOk(): Boolean {
        //TODO: posIsOk implementation
        return true
    }

    fun doMove(m: Move, newSt: StateInfo) {
        doMove(m, newSt, givesCheck(m))

    }


    fun givesCheck(m: Move): Boolean {
        assert(isOk(m))
        assert(colorOf(movedPiece(m)) == sideToMove)

        val from: Square = fromSq(m)
        val to: Square = toSq(m)

        // Is there a direct check?
        //TODO:  check if SquareBB[to.value] is correct
        if ((checkSquares(typeOf(pieceOn(from))) and SquareBB[to.value]) != 0UL) return true

        //Is there a discovered check?
        if ((blockersForKing(sideToMove) and SquareBB[from.value] != 0UL) && !aligned(
                from, to, square(KING, sideToMove)
            )
        ) return when (typeOf(m)) {
            NORMAL -> false
            PROMOTION -> attacksBb(
                promotionType(m), to, pieces() xor SquareBB[from.value]
            ) and SquareBB[square(KING, sideToMove).value] != 0UL

            /* En passant capture with check? We have already handled the case
             * of direct checks and ordinary discovered check, so the only case we
             * need to handle is the unusual case of a discovered check through
             * the captured pawn.
             */
            EN_PASSANT -> {
                var capsq: Square = makeSquare(fileOf(to), rankOf(from))
                var b: Bitboard = (pieces() xor from xor capsq) or SquareBB[to.value]
                return (attacksBb(ROOK, square(KING, sideToMove), b) and (pieces(
                    sideToMove, QUEEN, ROOK
                ) or (attacksBb(BISHOP, square(KING, sideToMove), b) and (pieces(
                    sideToMove, QUEEN, BISHOP
                ))))) != 0UL
            }
            //CASTLING
            else -> {
                // Castling moves are encoded as 'king captures the rook'
                //TODO: check if  the Squares for castling are correct
                var ksq: Square = square(KING, sideToMove)
                var rto: Square = relativeSquare(sideToMove, if (to > from) SQ_F1 else SQ_D1)

                //TODO: Check if the occupancy is correct
                return (attacksBb(
                    ROOK, rto, pieces() xor ksq xor rto
                ) and SquareBB[ksq.value] != 0UL)
            }
        }
        return false
    }

    private fun promotionType(m: Move): PieceType {
        TODO("Not yet implemented")
    }

    fun square(pt: PieceType, sideToMove: Color): Square {
        assert(count(sideToMove, pt) == 1)

        return Square.values()[lsb(pieces(sideToMove, pt))]

    }


    fun doMove(m: Move, newSt: StateInfo, givesCheck: Boolean) {
        assert(isOk(m))
        assert(newSt != st)
        //TODO
        // Key k = st->key ^ Zobrist::side;

        /* Copy some fields of the old state to our new StateInfo object except the
         * ones which are going to be recalculated from scratch anyway and then switch
         * our state pointer to point to the new (ready to be updated) state.
         * */
        newSt.previous = st
        st = newSt/* Increment ply counters. In particular, rule50 will be reset to zero later on
         * in case of capture or pawn move.
         */
        gamePly++
        st.rule50++

        //TODO: Check if pliesfromnull is correct
        st.pliesFromNull = 0

        var us: Color = sideToMove
        var them = Color.values()[us.ordinal xor 1]
        var from = fromSq(m)
//        var to = toSq(m)
        var to: Deque<Square> = ArrayDeque()
        to.add(toSq(m))
        var pc = pieceOn(from)
        var captured: Piece =
            if (typeOf(m) == MoveType.EN_PASSANT) makePiece(them, PAWN) else pieceOn(to.first)
        assert(colorOf(pc) == us)
        assert(captured == NO_PIECE || colorOf(captured) == (if (typeOf(m) != MoveType.CASTLING) them else us))
        assert(typeOf(captured) != KING)

        if (typeOf(m) == MoveType.CASTLING) {
            assert(pc == makePiece(us, KING))
            assert(captured == makePiece(us, ROOK))
            var rfrom: Deque<Square> = ArrayDeque()
            var rto: Deque<Square> = ArrayDeque()

            doCastling(us, from, to, rfrom, rto, true)


        }


    }


    private fun doCastling(
        us: Color,
        from: Square,
        to: Deque<Square>,
        rfrom: Deque<Square>,
        rto: Deque<Square>,
        Do: Boolean = false,
    ) {
        var kingSide: Boolean = to.peek() > from
        rfrom.add(to.first())
        rto.add(relativeSquare(us, if (kingSide) SQ_F1 else SQ_D1))
        to.add((relativeSquare(us, if (kingSide) SQ_F1 else SQ_D1)))


        if (Do.equals(true)) {
            removePiece(from)
            removePiece(to.first())
            putPiece(makePiece(us, KING), to.first())
            putPiece(makePiece(us, ROOK), rto.first())
        }

    }

    fun canCastle(cr: Int): Boolean {
        return st.castlingRights and cr != 0
    }

    fun castlingImpeded(cr: CastlingRights): Boolean {
        assert(cr == WHITE_OO || cr == WHITE_OOO || cr == BLACK_OO || cr == BLACK_OOO)

        return pieces() and castlingPath[cr.value] != 0UL

    }

    fun castlingRookSquare(cr: CastlingRights): Square {
        assert(cr == WHITE_OO || cr == WHITE_OOO || cr == BLACK_OO || cr == BLACK_OOO)

        return castlingRookSquare[cr.value]


    }


}



