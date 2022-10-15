//[ChessNet](../../../../index.md)/[chessnet](../../index.md)/[Uci](../index.md)/[Companion](index.md)/[move](move.md)

# move

[jvm]\
fun [move](move.md)(m: [Move](../../-move/index.md), chess960: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

move() converts a Move to a string in coordinate notation (g1f3, a7a8q). The only special case is castling where the e1g1 notation is printed in standard chess mode and in e1h1 notation it is printed in Chess960 mode. Internally, all castling moves are always encoded as 'king captures rook'.
