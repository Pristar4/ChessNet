//[ChessNet](../../../index.md)/[chessnet](../index.md)/[Position](index.md)

# Position

[jvm]\
class [Position](index.md)

## Constructors

| | |
|---|---|
| [Position](-position.md) | [jvm]<br>fun [Position](-position.md)() |

## Functions

| Name | Summary |
|---|---|
| [blockersForKing](blockers-for-king.md) | [jvm]<br>fun [blockersForKing](blockers-for-king.md)(c: [Color](../-color/index.md)): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040) |
| [canCastle](can-castle.md) | [jvm]<br>fun [canCastle](can-castle.md)(cr: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [castlingImpeded](castling-impeded.md) | [jvm]<br>fun [castlingImpeded](castling-impeded.md)(cr: [CastlingRights](../-castling-rights/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [castlingRookSquare](castling-rook-square.md) | [jvm]<br>fun [castlingRookSquare](castling-rook-square.md)(cr: [CastlingRights](../-castling-rights/index.md)): [Square](../-square/index.md) |
| [checkers](checkers.md) | [jvm]<br>fun [checkers](checkers.md)(): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040) |
| [count](count.md) | [jvm]<br>fun [count](count.md)(c: [Color](../-color/index.md), pt: [PieceType](../-piece-type/index.md)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [doMove](do-move.md) | [jvm]<br>fun [doMove](do-move.md)(m: [Move](../-move/index.md), newSt: [StateInfo](../-state-info/index.md))<br>fun [doMove](do-move.md)(m: [Move](../-move/index.md), newSt: [StateInfo](../-state-info/index.md), givesCheck: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [epSquare](ep-square.md) | [jvm]<br>fun [epSquare](ep-square.md)(): [Square](../-square/index.md) |
| [givesCheck](gives-check.md) | [jvm]<br>fun [givesCheck](gives-check.md)(m: [Move](../-move/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [init](init.md) | [jvm]<br>fun [init](init.md)() |
| [movedPiece](moved-piece.md) | [jvm]<br>inline fun [movedPiece](moved-piece.md)(m: [Move](../-move/index.md)): [Piece](../-piece/index.md) |
| [pieceOn](piece-on.md) | [jvm]<br>fun [pieceOn](piece-on.md)(s: [Square](../-square/index.md)): [Piece](../-piece/index.md) |
| [pieces](pieces.md) | [jvm]<br>fun [pieces](pieces.md)(c: [Color](../-color/index.md)): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [pieces](pieces.md)(pt: [PieceType](../-piece-type/index.md) = ALL_PIECES): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [pieces](pieces.md)(c: [Color](../-color/index.md), pt: [PieceType](../-piece-type/index.md)): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [pieces](pieces.md)(pt1: [PieceType](../-piece-type/index.md), pt2: [PieceType](../-piece-type/index.md)): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [pieces](pieces.md)(c: [Color](../-color/index.md), pt1: [PieceType](../-piece-type/index.md), pt2: [PieceType](../-piece-type/index.md)): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [pieces](pieces.md)(pt1: [PieceType](../-piece-type/index.md), pt2: [PieceType](../-piece-type/index.md), pt3: [PieceType](../-piece-type/index.md)): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040) |
| [set](set.md) | [jvm]<br>fun [set](set.md)(fenStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isChess960: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), si: [StateInfo](../-state-info/index.md)): [Position](index.md)<br>[set](set.md) () initializes the position object with the given FEN string. This function is not very robust - make sure that input FENs are correct, this is assumed to be the responsibility of the GUI. |
| [setCastlingRight](set-castling-right.md) | [jvm]<br>fun [setCastlingRight](set-castling-right.md)(c: [Color](../-color/index.md), rfrom: [Deque](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html)&lt;[Square](../-square/index.md)&gt;) |
| [square](square.md) | [jvm]<br>fun [square](square.md)(pt: [PieceType](../-piece-type/index.md), sideToMove: [Color](../-color/index.md)): [Square](../-square/index.md) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [board](board.md) | [jvm]<br>var [board](board.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Piece](../-piece/index.md)&gt; |
| [byColorBB](by-color-b-b.md) | [jvm]<br>var [byColorBB](by-color-b-b.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [byTypeBB](by-type-b-b.md) | [jvm]<br>var [byTypeBB](by-type-b-b.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [castlingPath](castling-path.md) | [jvm]<br>var [castlingPath](castling-path.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [castlingRightsMask](castling-rights-mask.md) | [jvm]<br>var [castlingRightsMask](castling-rights-mask.md): [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html) |
| [castlingRookSquare](castling-rook-square.md) | [jvm]<br>var [castlingRookSquare](castling-rook-square.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Square](../-square/index.md)&gt; |
| [chess960](chess960.md) | [jvm]<br>var [chess960](chess960.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
| [gamePly](game-ply.md) | [jvm]<br>var [gamePly](game-ply.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [pieceCount](piece-count.md) | [jvm]<br>var [pieceCount](piece-count.md): [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html) |
| [psq](psq.md) | [jvm]<br>var [psq](psq.md): [Score](../-score/index.md) |
| [sideToMove](side-to-move.md) | [jvm]<br>var [sideToMove](side-to-move.md): [Color](../-color/index.md) |
| [StateList](-state-list.md) | [jvm]<br>var [StateList](-state-list.md): [Deque](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html)&lt;[StateInfo](../-state-info/index.md)&gt;<br>A list to keep track of the position states along the setup moves( from the start position to the position just before the search starts). Needed by 'draw by repetition' detection. use deque because we need to be able to remove the last element |
| [thisThread](this-thread.md) | [jvm]<br>var [thisThread](this-thread.md): [Thread](https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)? = null |
