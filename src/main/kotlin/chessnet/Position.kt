package chessnet

import chessnet.PieceType.*
import chessnet.Piece.*
import chessnet.Color.*
import chessnet.CastlingRights.*
import chessnet.Square.*
import chessnet.Direction.*
import chessnet.MoveType.*
import java.util.*
import kotlin.math.max

/**  A list to keep track of the position states along the setup moves( from the start position to the position
 *  just before the search starts). Needed by 'draw by repetition' detection.
 *  use deque because we need to be able to remove the last element
 */
var StateList: Deque<StateInfo> = ArrayDeque<StateInfo>()

/**
 * The Position class stores information about the board representation, hash keys, castling rights,
 * en-passant square, rule 50 counter, and the side to move. It also holds a list of the positions
 * along the setup moves (from the start position to the position just before the search starts).
 * @param fen the FEN string representing the position
 * @param isChess960 whether the position is in Chess960 mode
 * @param si the state information
 */
class Position {
    var fen = ""

    // returns an ASCII representation of the position
    override fun toString(): String {
        var s = ""
        var f = File.FILE_A.value
        var r = Rank.RANK_8.value
        var piece: Piece
        s += " +---+---+---+---+---+---+---+---+\n"
        for (r_ in Rank.RANK_8.value downTo Rank.RANK_1.value) {
            for (f_ in File.FILE_A.value..File.FILE_H.value) {
                piece = pieceOn(makeSquare(File.values()[f_], Rank.values()[r_]))
                s += " | " + if (piece == NO_PIECE) "." else "$piece"
                f++
                s += if (f > File.FILE_H.value) " |" else ""
            }
            s += "\n +---+---+---+---+---+---+---+---+\n"
            f = 0
            r--
        }
        s += "FEN: $fen\n"
        s += "sideToMove: $sideToMove\n"
        s += "castlingRights: ${st.castlingRights}\n"
        s += "epSquare: ${st.epSquare}\n"
        s += "gamePly: $gamePly\n"
        s += "psq: $psq\n"
        return s
    }

    private fun init() {
        TODO()
    }


    /**
     * clear() resets the position object to the initial state.
     */
    private fun clear() {
        byTypeBB = Array(PIECE_TYPE_NB.value) { 0UL }
        byColorBB = Array(COLOR_NB.value) { 0UL }
        board = Array(SQUARE_NB.value) { NO_PIECE }
        pieceCount = Array(PIECE_NB.value) { 0 }
    }


    // FEN string input/output
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
        fen = fenStr
        var token: Char
        var col = 0
        var row = 7 // 7 because we start at the top of the board
        var sq: Square
        val ss = Scanner(fenStr)

//        std::memset(this, 0, sizeof(Position));
//        std::memset(si, 0, sizeof(StateInfo)); in kotlin

        clear()
        st = si
        ss.useDelimiter("")
        //TODO: Check if there is missing bug for values that are not being reset here

        // 1. Piece placement

        while (ss.hasNext() && !ss.hasNext("\\s")) {
            token = ss.next().single()
            if (token.isDigit()) {
                col += token.toString().toInt() // Advance the given number of files


            } else if (token == '/') {
                row--
                col = 0

            } else if (Piece.getPiece(token) != NO_PIECE) {

                sq = makeSquare(File.values()[col], Rank.values()[row])
                putPiece(Piece.getPiece(token), sq)
                ++col
            } else {
                throw Exception("FEN error: invalid character '$token' in piece placement field")
            }
        }
        // 2. Active color

        ss.next().single()  // Consume " "
        token = ss.next().single() // Consume "w" or "b"
        sideToMove = if (token == 'w') WHITE else BLACK
        token = ss.next().single() // Consume "w" or "b"

        /** 3. Castling availability. Compatible with 3 standards: Normal FEN standard,
        Shredder-FEN that uses the letters of the columns on which the rooks began
        the game instead of KQkq and also X-FEN standard that, in case of Chess960,
        if an inner rook is associated with the castling right, the castling tag is
        replaced by the file letter of the involved rook, as for the Shredder-FEN.*/
        while (ss.hasNext() && !ss.hasNext("\\s")) {
            var rsq: Square = SQ_NONE
            val c: Color = if (token.isLowerCase()) BLACK else WHITE
            var rook: Piece = makePiece(c, ROOK)
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
        val enpassant = false
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
        val gamePlyRight = (if (sideToMove == BLACK) 1 else 0)

        gamePly = max(
            2 * (gamePly - 1), 0
        ) + gamePlyRight
        //TODO: check if this gamePly number is correct

        this.isChess960 = isChess960
//        thisThread = Thread.currentThread()
        //thisThread = th
        setState(st)
        assert(posIsOk())
        return this

    }


    fun fen(): String {
        //TODO: implement real fen
        return this.fen
    }

    // Position representation
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

    fun pieceOn(s: Square): Piece {
        assert(isOk(s))
        return board[s.value]
    }

    fun epSquare(): Square {
        return st.epSquare
    }

    fun attackersTo(s: Square): Bitboard {
        return attackersTo(s, pieces())

    }

    /**
     * attackersTo() returns a bitboard representing all pieces of the given color
     * that attack a given square.
     * @param s Square to which the attacking pieces must be computed
     * @param occupied Bitboard representing all the occupied squares
     */
    private fun attackersTo(s: Square, occupied: Bitboard): Bitboard {
        return (pawnAttacksBb(BLACK, s.value) and pieces(WHITE, PAWN)) or ((pawnAttacksBb(WHITE, s.value) and pieces(
            BLACK,
            PAWN
        )) or (attacksBb(KNIGHT, s) and pieces(KNIGHT)) or (attacksBb(s, occupied, ROOK) and pieces(
            ROOK,
            QUEEN
        )) or (attacksBb(s, occupied, BISHOP) and pieces(BISHOP, QUEEN)) or (attacksBb(KING, s) and pieces(KING)))


    }

    fun count(c: Color, pt: PieceType): Int {
        return pieceCount[makePiece(c, pt).value]
    }

    fun square(pt: PieceType, c: Color): Square {
        assert(count(c, pt) == 1)
        return lsb(pieces(c, pt))
    }

    //Castling
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

    //Checking
    fun blockersForKing(c: Color): Bitboard {

        return st.blockersForKing[c.value]
    }

    fun checkSquares(pt: PieceType): Bitboard {

        return st.checkSquares[pt.value]


    }

    fun checkers(): Bitboard {
        return st.checkersBB

    }

    // Attacks to/from a given square

    // Properties of moves
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
                to, pieces() xor SquareBB[from.value], promotionType(m)
            ) and SquareBB[square(KING, sideToMove).value] != 0UL

            /* En passant capture with check? We have already handled the case
             * of direct checks and ordinary discovered check, so the only case we
             * need to handle is the unusual case of a discovered check through
             * the captured pawn.
             */
            EN_PASSANT -> {
                val capsq: Square = makeSquare(fileOf(to), rankOf(from))
                val b: Bitboard = (pieces() xor squareBb(from) xor squareBb(capsq)) or SquareBB[to.value]
                return (attacksBb(

                    square(KING, sideToMove), b, ROOK
                ) and (pieces(
                    sideToMove, QUEEN, ROOK
                ) or (attacksBb(

                    square(KING, sideToMove), b, BISHOP
                ) and (pieces(
                    sideToMove, QUEEN, BISHOP
                ))))) != 0UL
            }
            //CASTLING
            else -> {
                // Castling moves are encoded as 'king captures the rook'
                //TODO: check if  the Squares for castling are correct
                val ksq: Square = square(KING, sideToMove)
                val rto: Square = relativeSquare(sideToMove, if (to > from) SQ_F1 else SQ_D1)

                //TODO: Check if the occupancy is correct
                return (attacksBb(
                    rto, pieces() xor squareBb(ksq) xor squareBb(rto), ROOK
                ) and SquareBB[ksq.value] != 0UL)
            }
        }
        return false
    }

    fun movedPiece(m: Move): Piece {
        return pieceOn(fromSq(m))
    }

    // Piece specific

    // Doing and undoing moves
    fun doMove(m: Move, newSt: StateInfo) {
        doMove(m, newSt, givesCheck(m))

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

        val us: Color = sideToMove
        val them = Color.values()[us.ordinal xor 1]
        val from = fromSq(m)
//        var to = toSq(m)
        val to: Square = toSq(m)
        val pc = pieceOn(from)
        val captured: Piece = if (typeOf(m) == EN_PASSANT) {
            makePiece(them, PAWN)
        } else {
            pieceOn(to)
        }

        assert(colorOf(pc) == us)
        assert(captured == NO_PIECE || colorOf(captured) == (if (typeOf(m) != CASTLING) them else us))
        assert(typeOf(captured) != KING)

        if (typeOf(m) == CASTLING) {
            assert(pc == makePiece(us, KING))
            assert(captured == makePiece(us, ROOK))
            val rfrom: Square = SQ_NONE
            val rto: Square = SQ_NONE

            doCastling(us, from, to, rfrom, rto, true)


        }


        if (captured != NO_PIECE) {
            var capsq: Square = to

            // If the captured piece is a pawn, update pawn hash key, otherwise
            // update non-pawn material.
            if (typeOf(captured) == PAWN){
                if (typeOf(m) == EN_PASSANT){
                    capsq -= pawnPush(us)

                    assert(pc == makePiece(us, PAWN))
                    assert(to == st.epSquare)
                    assert(relativeRank(us, to) == Rank.RANK_6)
                    assert(pieceOn(to) == NO_PIECE)
                    assert(pieceOn(capsq) == makePiece(them, PAWN))
                }
                //TODO: implement Zobrish hashing
//                st.pawnKey ^= Zobrist.psq[capsq.value][captured.value]

            } else{
//                st.nonPawnMaterial[them.value] -= PieceValue[MG][captured.value]
                //TODO: implement Zobrish hashing
            }

            // Update board and piece lists
            removePiece(capsq)

            if (typeOf(m) == EN_PASSANT)
                board[capsq.value] = NO_PIECE
            // Update material hash key and prefetch access to materialTable
            //TODO: add material hash key and prefetch acces to materialTable

            //Reset rule50 counter
            st.rule50 = 0

        }

        // Reset en passant square
        if (st.epSquare != SQ_NONE){
            st.epSquare = SQ_NONE
        }
        // Update castling rights if needed
        if (st.castlingRights != 0 && (castlingRightsMask[from.value] or castlingRightsMask[to.value]) != 0) {
            st.castlingRights = st.castlingRights and castlingRightsMask[from.value].inv() and castlingRightsMask[to.value]
        }

        // Move the piece. The tricky Chess960 castling is handled earlier
        if (typeOf(m) != CASTLING) {
           movePiece(from,to)
        }
    }


    // Static Exchange Evaluation

    // Accessing hash keys

    // Other properties of the position

    // Position consistency check, for debugging
    fun posIsOk(): Boolean {
        //TODO: posIsOk implementation
        return true
    }


    // Used by NNUE
    fun putPiece(piece: Piece, s: Square) {
        board[s.ordinal] = piece
        byTypeBB[ALL_PIECES.value] = byTypeBB[ALL_PIECES.value] or squareBb(s)
        byTypeBB[typeOf(piece).value] = byTypeBB[typeOf(piece).value] or SquareBB[s.value]
        byColorBB[colorOf(piece).value] = byColorBB[colorOf(piece).value] or squareBb(s)
        pieceCount[piece.value]++
        pieceCount[makePiece(colorOf(piece), ALL_PIECES).value]++
        //FIXME: psq does not work
//        psq = psq + psq[piece.value][s.ordinal]
    }

    private fun removePiece(s: Square) {
        var pc: Piece = board[s.ordinal]
        byTypeBB[ALL_PIECES.value] = byTypeBB[ALL_PIECES.value] xor squareBb(s)
        byTypeBB[typeOf(pc).value] = byTypeBB[typeOf(pc).value] xor squareBb(s)
        byColorBB[colorOf(pc).value] = byColorBB[colorOf(pc).value] xor squareBb(s)
        board[s.ordinal] = NO_PIECE
        pieceCount[pc.value]--
        pieceCount[makePiece(colorOf(pc), ALL_PIECES).value]--
    }

    // Initialization helpers (used while setting up a position)
    private fun setState(si: StateInfo) {
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

    // Other helpers
    fun movePiece(from: Square, to: Square) {
        var pc = board[from.value]
        var fromTo:Bitboard = squareBb(from) or squareBb(to)
        byTypeBB[ALL_PIECES.value] = byTypeBB[ALL_PIECES.value] xor fromTo
        byTypeBB[typeOf(pc).value] = byTypeBB[typeOf(pc).value] xor fromTo
        byColorBB[colorOf(pc).value] = byColorBB[colorOf(pc).value] xor fromTo
        board[from.value] = NO_PIECE
        board[to.value] = pc

        //TODO: add psq here
//        psq += PSQT::psq[pc][to] - PSQT::psq[pc][from];


    }
    private fun doCastling(
        us: Color, from: Square, _to: Square, _rfrom: Square, _rto: Square, doIt: Boolean = false
    ) {
        val kingSide: Boolean = _to > from
        val rfrom = _to
        val rto = (relativeSquare(us, if (kingSide) SQ_F1 else SQ_D1))
        val to = ((relativeSquare(us, if (kingSide) SQ_F1 else SQ_D1)))


        removePiece(
            if (doIt) {
                from
            } else {
                to
            }
        )
        removePiece(
            if (doIt) {
                rfrom
            } else {
                rto
            }
        )
//        board[Do ? from : to] = board[Do ? rfrom : rto] = NO_PIECE; in kotlin
        board[if (doIt) {
            from
        } else {
            to
        }.value] = NO_PIECE
        board[if (doIt) {
            rfrom
        } else {
            rto
        }.value] = NO_PIECE
        putPiece(
            makePiece(us, KING), if (doIt) {
                to
            } else {
                from
            }
        )
        putPiece(
            makePiece(us, ROOK), if (doIt) {
                rto
            } else {
                rfrom
            }
        )

    }

    fun legal(m: Move): Boolean {
        assert(isOk(m))

        var us: Color = sideToMove
        var from: Square = fromSq(m)
        var to: Square = toSq(m)

        assert(colorOf(movedPiece(m)) == us)
        assert(pieceOn(square(KING, us)) == makePiece(us, KING))

        // En passant captures are a tricky special case. Because they are rather
        // uncommon, we do it simply by testing whether the king is attacked after
        // the move is made.
        if (typeOf(m) == EN_PASSANT) {
            var ksq: Square = square(KING, us)
            var capsq: Square = to - pawnPush(us)
            var occupied: Bitboard = (pieces() xor squareBb(from) xor squareBb(capsq)) or squareBb(to)

            assert(to == st.epSquare)
            assert(movedPiece(m) == makePiece(us, PAWN))
            assert(pieceOn(capsq) == makePiece(us.opposite(), PAWN))
            assert(pieceOn(to) == NO_PIECE)

            return !(attacksBb(ksq, occupied, ROOK) and pieces(us.opposite(), QUEEN, ROOK)) && !(attacksBb(
                ksq,
                occupied,
                BISHOP
            ) and pieces(us.opposite(), QUEEN, BISHOP))
        }
        // Castling moves generation does not check if the castling path is clear of
        // enemy attacks, it is delayed at a later time: now!
        if (typeOf(m) == CASTLING) {
            // After castling, the rook and king final positions are the same in
            // Chess960 as they would be in standard chess.
            to = relativeSquare(us, if (to > from) SQ_G1 else SQ_C1)
            var step: Direction = if (to > from) WEST else EAST

            //for each square between the rook and king
            for (s: Square in squaresBetween(from, to, step)) {
                if (attackersTo(s) and pieces(us.opposite()) == 0uL) {
                    return false
                }
            }
            // In case of Chess960, verify if the Rook blocks some checks
            // For instance an enemy queen in SQ_A1 when castling rook is in SQ_B1.
            return !isChess960 || !(blockersForKing(us) and pieces(us.opposite()))
        }
        // If the moving piece is a king, check whether the destination square is
        // attacked by the opponent.
        if (typeOf(pieceOn(from)) == KING)
            return !(attackersTo(to, pieces() xor squareBb(from)) and squareBb(toSq(m)))
        // A non-king move is legal if and only if it is not pinned or it
        // is moving along the ray towards or away from the king.
        return !(blockersForKing(us) and squareBb(from))
                || aligned(from, to, square(KING, us)) == 0uL
    }

    private fun squaresBetween(from: Square, to: Square, step: Direction): List<Square> {
        var squares: MutableList<Square> = mutableListOf()
        var s: Square = from + step
        while (s != to) {
            squares.add(s)
            s += step
        }
        return squares
    }


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
    var isChess960: Boolean = false




}