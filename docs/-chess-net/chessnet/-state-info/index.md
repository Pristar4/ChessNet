//[ChessNet](../../../index.md)/[chessnet](../index.md)/[StateInfo](index.md)

# StateInfo

[jvm]\
class [StateInfo](index.md)

## Constructors

| | |
|---|---|
| [StateInfo](-state-info.md) | [jvm]<br>fun [StateInfo](-state-info.md)() |

## Properties

| Name | Summary |
|---|---|
| [blockersForKing](blockers-for-king.md) | [jvm]<br>var [blockersForKing](blockers-for-king.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [castlingRights](castling-rights.md) | [jvm]<br>var [castlingRights](castling-rights.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [checkersBB](checkers-b-b.md) | [jvm]<br>var [checkersBB](checkers-b-b.md): [Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040) |
| [checkSquares](check-squares.md) | [jvm]<br>var [checkSquares](check-squares.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [epSquare](ep-square.md) | [jvm]<br>var [epSquare](ep-square.md): [Square](../-square/index.md) |
| [key](key.md) | [jvm]<br>var [key](key.md): [Key](../index.md#1218344708%2FClasslikes%2F-1216412040) |
| [materialKey](material-key.md) | [jvm]<br>var [materialKey](material-key.md): [Key](../index.md#1218344708%2FClasslikes%2F-1216412040) |
| [pawnKey](pawn-key.md) | [jvm]<br>var [pawnKey](pawn-key.md): [Key](../index.md#1218344708%2FClasslikes%2F-1216412040) |
| [pinnersForKing](pinners-for-king.md) | [jvm]<br>var [pinnersForKing](pinners-for-king.md): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[Bitboard](../index.md#610777926%2FClasslikes%2F-1216412040)&gt; |
| [pliesFromNull](plies-from-null.md) | [jvm]<br>var [pliesFromNull](plies-from-null.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [previous](previous.md) | [jvm]<br>var [previous](previous.md): [StateInfo](index.md)? = null |
| [repitition](repitition.md) | [jvm]<br>var [repitition](repitition.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
| [rule50](rule50.md) | [jvm]<br>var [rule50](rule50.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0 |
