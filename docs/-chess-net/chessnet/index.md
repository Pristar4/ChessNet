//[ChessNet](../../index.md)/[chessnet](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [_Color](_-color/index.md) | [jvm]<br>enum [_Color](_-color/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[_Color](_-color/index.md)&gt; |
| [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) | [jvm]<br>typealias [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) = [ULong](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-long/index.html) |
| [Bitboards](-bitboards/index.md) | [jvm]<br>class [Bitboards](-bitboards/index.md) |
| [Board](-board/index.md) | [jvm]<br>class [Board](-board/index.md) |
| [CastlingRights](-castling-rights/index.md) | [jvm]<br>enum [CastlingRights](-castling-rights/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[CastlingRights](-castling-rights/index.md)&gt; |
| [Color](-color/index.md) | [jvm]<br>enum [Color](-color/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Color](-color/index.md)&gt; |
| [Coord](-coord/index.md) | [jvm]<br>data class [Coord](-coord/index.md)(val x: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val y: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [Direction](-direction/index.md) | [jvm]<br>enum [Direction](-direction/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Direction](-direction/index.md)&gt; |
| [File](-file/index.md) | [jvm]<br>enum [File](-file/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[File](-file/index.md)&gt; |
| [Key](index.md#1218344708%2FClasslikes%2F-1216412040) | [jvm]<br>typealias [Key](index.md#1218344708%2FClasslikes%2F-1216412040) = [ULong](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-long/index.html) |
| [Magic](-magic/index.md) | [jvm]<br>class [Magic](-magic/index.md)(val mask: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040), val magic: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040), val attacks: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt;, val shift: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [Move](-move/index.md) | [jvm]<br>class [Move](-move/index.md)(val value: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0) |
| [MoveType](-move-type/index.md) | [jvm]<br>enum [MoveType](-move-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[MoveType](-move-type/index.md)&gt; |
| [newSquare](new-square/index.md) | [jvm]<br>class [newSquare](new-square/index.md)(num: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [Piece](-piece/index.md) | [jvm]<br>enum [Piece](-piece/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Piece](-piece/index.md)&gt; |
| [PieceType](-piece-type/index.md) | [jvm]<br>enum [PieceType](-piece-type/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[PieceType](-piece-type/index.md)&gt; |
| [Position](-position/index.md) | [jvm]<br>class [Position](-position/index.md) |
| [Rank](-rank/index.md) | [jvm]<br>enum [Rank](-rank/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Rank](-rank/index.md)&gt; |
| [Score](-score/index.md) | [jvm]<br>enum [Score](-score/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Score](-score/index.md)&gt; |
| [Square](-square/index.md) | [jvm]<br>enum [Square](-square/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Square](-square/index.md)&gt; <br>A square on a chess board. |
| [StateInfo](-state-info/index.md) | [jvm]<br>class [StateInfo](-state-info/index.md) |
| [Tile](-tile/index.md) | [jvm]<br>class [Tile](-tile/index.md)(val coord: [Coord](-coord/index.md)) |
| [Uci](-uci/index.md) | [jvm]<br>class [Uci](-uci/index.md) |
| [Value](-value/index.md) | [jvm]<br>enum [Value](-value/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Value](-value/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [aligned](aligned.md) | [jvm]<br>fun [aligned](aligned.md)(s1: [Square](-square/index.md), s2: [Square](-square/index.md), s3: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [attacksBb](attacks-bb.md) | [jvm]<br>fun [attacksBb](attacks-bb.md)(Pt: [PieceType](-piece-type/index.md), s: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [attacksBb](attacks-bb.md)(Pt: [PieceType](-piece-type/index.md), s1: [Square](-square/index.md), occupied: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [betweenBb](between-bb.md) | [jvm]<br>fun [betweenBb](between-bb.md)(s1: [Square](-square/index.md), s2: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [bishopAttacksBb](bishop-attacks-bb.md) | [jvm]<br>fun [bishopAttacksBb](bishop-attacks-bb.md)(s: [Square](-square/index.md), occupied: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [BitScanForward](-bit-scan-forward.md) | [jvm]<br>fun [BitScanForward](-bit-scan-forward.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [BitScanReverse](-bit-scan-reverse.md) | [jvm]<br>fun [BitScanReverse](-bit-scan-reverse.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [CastlingRights](-castling-rights.md) | [jvm]<br>fun [CastlingRights](-castling-rights.md)(c: [Color](-color/index.md), cr: [CastlingRights](-castling-rights/index.md)): [CastlingRights](-castling-rights/index.md) |
| [colorOf](color-of.md) | [jvm]<br>fun [colorOf](color-of.md)(pc: [Piece](-piece/index.md)): [Color](-color/index.md) |
| [distance](distance.md) | [jvm]<br>inline fun &lt;[T](distance.md)&gt; [distance](distance.md)(x: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), y: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [engineInfo](engine-info.md) | [jvm]<br>fun [engineInfo](engine-info.md)(toUci: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [fileBb](file-bb.md) | [jvm]<br>fun [fileBb](file-bb.md)(f: [File](-file/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [fileBb](file-bb.md)(s: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [fileOf](file-of.md) | [jvm]<br>fun [fileOf](file-of.md)(s: [Square](-square/index.md)): [File](-file/index.md) |
| [fromSq](from-sq.md) | [jvm]<br>fun [fromSq](from-sq.md)(m: [Move](-move/index.md)): [Square](-square/index.md) |
| [get](get.md) | [jvm]<br>operator fun [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040).[get](get.md)(s: [Square](-square/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isOk](is-ok.md) | [jvm]<br>fun [isOk](is-ok.md)(m: [Move](-move/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [isOk](is-ok.md)(s: [Square](-square/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [isOk](is-ok.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lineBb](line-bb.md) | [jvm]<br>fun [lineBb](line-bb.md)(s1: [Square](-square/index.md), s2: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [lsb](lsb.md) | [jvm]<br>inline fun [lsb](lsb.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Square](-square/index.md) |
| [makeMove](make-move.md) | [jvm]<br>fun [makeMove](make-move.md)(from: [Square](-square/index.md), to: [Square](-square/index.md)): [Move](-move/index.md) |
| [makePiece](make-piece.md) | [jvm]<br>fun [makePiece](make-piece.md)(c: [Color](-color/index.md), pt: [PieceType](-piece-type/index.md)): [Piece](-piece/index.md) |
| [makeScore](make-score.md) | [jvm]<br>fun [makeScore](make-score.md)(mg: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), eg: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Score](-score/index.md) |
| [moreThanOne](more-than-one.md) | [jvm]<br>fun [moreThanOne](more-than-one.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [msb](msb.md) | [jvm]<br>inline fun [msb](msb.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Square](-square/index.md) |
| [not](not.md) | [jvm]<br>operator fun [ULong](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-long/index.html).[not](not.md)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [operatorMinus](operator-minus.md) | [jvm]<br>fun [operatorMinus](operator-minus.md)(s: [Square](-square/index.md), d: [Direction](-direction/index.md)): [Square](-square/index.md) |
| [operatorPlus](operator-plus.md) | [jvm]<br>fun [operatorPlus](operator-plus.md)(s: [Square](-square/index.md), d: [Direction](-direction/index.md)): [Square](-square/index.md) |
| [oppositeColors](opposite-colors.md) | [jvm]<br>fun [oppositeColors](opposite-colors.md)() |
| [pawnAttacksBb](pawn-attacks-bb.md) | [jvm]<br>fun [pawnAttacksBb](pawn-attacks-bb.md)(c: [Color](-color/index.md), s1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [pawnPush](pawn-push.md) | [jvm]<br>fun [pawnPush](pawn-push.md)(c: [Color](-color/index.md)): [Direction](-direction/index.md) |
| [PieceType](-piece-type.md) | [jvm]<br>fun [PieceType](-piece-type.md)(value: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [PieceType](-piece-type/index.md) |
| [popLsb](pop-lsb.md) | [jvm]<br>fun [popLsb](pop-lsb.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[Square](-square/index.md), [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [rankBb](rank-bb.md) | [jvm]<br>fun [rankBb](rank-bb.md)(r: [Rank](-rank/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)<br>fun [rankBb](rank-bb.md)(s: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [rankOf](rank-of.md) | [jvm]<br>fun [rankOf](rank-of.md)(s: [Square](-square/index.md)): [Rank](-rank/index.md) |
| [relativeRank](relative-rank.md) | [jvm]<br>fun [relativeRank](relative-rank.md)(c: [Color](-color/index.md), r: [Rank](-rank/index.md)): [Rank](-rank/index.md)<br>fun [relativeRank](relative-rank.md)(c: [Color](-color/index.md), s: [Square](-square/index.md)): [Rank](-rank/index.md) |
| [relativeSquare](relative-square.md) | [jvm]<br>fun [relativeSquare](relative-square.md)(color: [Color](-color/index.md), square: [Square](-square/index.md)): [Square](-square/index.md) |
| [rookAttacksBb](rook-attacks-bb.md) | [jvm]<br>fun [rookAttacksBb](rook-attacks-bb.md)(s: [Square](-square/index.md), occupied: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [safeDestination](safe-destination.md) | [jvm]<br>fun [safeDestination](safe-destination.md)(s: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), step: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [shift](shift.md) | [jvm]<br>fun [shift](shift.md)(b: [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040), d: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Square](-square.md) | [jvm]<br>fun [Square](-square.md)(x: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), y: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [squareBb](square-bb.md) | [jvm]<br>fun [squareBb](square-bb.md)(s: [Square](-square/index.md)): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [toSq](to-sq.md) | [jvm]<br>fun [toSq](to-sq.md)(m: [Move](-move/index.md)): [Square](-square/index.md) |
| [typeOf](type-of.md) | [jvm]<br>fun [typeOf](type-of.md)(m: [Move](-move/index.md)): [MoveType](-move-type/index.md)<br>fun [typeOf](type-of.md)(pc: [Piece](-piece/index.md)): [PieceType](-piece-type/index.md) |

## Properties

| Name | Summary |
|---|---|
| [_b](_b.md) | [jvm]<br>var [_b](_b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [ALL_SQUARES](-a-l-l_-s-q-u-a-r-e-s.md) | [jvm]<br>const val [ALL_SQUARES](-a-l-l_-s-q-u-a-r-e-s.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [BetweenBB](-between-b-b.md) | [jvm]<br>val [BetweenBB](-between-b-b.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt;&gt; |
| [BishopMagics](-bishop-magics.md) | [jvm]<br>val [BishopMagics](-bishop-magics.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Magic](-magic/index.md)&gt; |
| [BOARD_SIZE](-b-o-a-r-d_-s-i-z-e.md) | [jvm]<br>val [BOARD_SIZE](-b-o-a-r-d_-s-i-z-e.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 8 |
| [Center](-center.md) | [jvm]<br>val [Center](-center.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [CenterFiles](-center-files.md) | [jvm]<br>val [CenterFiles](-center-files.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [DARK_SQUARES](-d-a-r-k_-s-q-u-a-r-e-s.md) | [jvm]<br>const val [DARK_SQUARES](-d-a-r-k_-s-q-u-a-r-e-s.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileABB](-file-a-b-b.md) | [jvm]<br>const val [FileABB](-file-a-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileBBB](-file-b-b-b.md) | [jvm]<br>val [FileBBB](-file-b-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileCBB](-file-c-b-b.md) | [jvm]<br>val [FileCBB](-file-c-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileDBB](-file-d-b-b.md) | [jvm]<br>val [FileDBB](-file-d-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileEBB](-file-e-b-b.md) | [jvm]<br>val [FileEBB](-file-e-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileFBB](-file-f-b-b.md) | [jvm]<br>val [FileFBB](-file-f-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileGBB](-file-g-b-b.md) | [jvm]<br>val [FileGBB](-file-g-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [FileHBB](-file-h-b-b.md) | [jvm]<br>val [FileHBB](-file-h-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [KingSide](-king-side.md) | [jvm]<br>val [KingSide](-king-side.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [LineBB](-line-b-b.md) | [jvm]<br>val [LineBB](-line-b-b.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt;&gt; |
| [PawnAttacks](-pawn-attacks.md) | [jvm]<br>val [PawnAttacks](-pawn-attacks.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt;&gt; |
| [PopCnt16](-pop-cnt16.md) | [jvm]<br>val [PopCnt16](-pop-cnt16.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[UByte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-byte/index.html)&gt; |
| [PseudoAttacks](-pseudo-attacks.md) | [jvm]<br>val [PseudoAttacks](-pseudo-attacks.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt;&gt; |
| [QueenSide](-queen-side.md) | [jvm]<br>val [QueenSide](-queen-side.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank1BB](-rank1-b-b.md) | [jvm]<br>const val [Rank1BB](-rank1-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank2BB](-rank2-b-b.md) | [jvm]<br>val [Rank2BB](-rank2-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank3BB](-rank3-b-b.md) | [jvm]<br>val [Rank3BB](-rank3-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank4BB](-rank4-b-b.md) | [jvm]<br>val [Rank4BB](-rank4-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank5BB](-rank5-b-b.md) | [jvm]<br>val [Rank5BB](-rank5-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank6BB](-rank6-b-b.md) | [jvm]<br>val [Rank6BB](-rank6-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank7BB](-rank7-b-b.md) | [jvm]<br>val [Rank7BB](-rank7-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [Rank8BB](-rank8-b-b.md) | [jvm]<br>val [Rank8BB](-rank8-b-b.md): [Bitboard](index.md#610777926%2FClasslikes%2F-1216412040) |
| [RookMagics](-rook-magics.md) | [jvm]<br>val [RookMagics](-rook-magics.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Magic](-magic/index.md)&gt; |
| [SquareBB](-square-b-b.md) | [jvm]<br>val [SquareBB](-square-b-b.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [SquareDistance](-square-distance.md) | [jvm]<br>val [SquareDistance](-square-distance.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[UByte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-u-byte/index.html)&gt;&gt; |
| [Version](-version.md) | [jvm]<br>var [Version](-version.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
