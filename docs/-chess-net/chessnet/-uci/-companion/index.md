//[ChessNet](../../../../index.md)/[chessnet](../../index.md)/[Uci](../index.md)/[Companion](index.md)

# Companion

[jvm]\
object [Companion](index.md)

## Functions

| Name | Summary |
|---|---|
| [go](go.md) | [jvm]<br>fun [go](go.md)(pos: [Position](../../-position/index.md), scanner: [Scanner](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html), states: [ArrayDeque](https://docs.oracle.com/javase/8/docs/api/java/util/ArrayDeque.html)&lt;[StateInfo](../../-state-info/index.md)&gt;) |
| [loop](loop.md) | [jvm]<br>fun [loop](loop.md)(argv: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |
| [move](move.md) | [jvm]<br>fun [move](move.md)(m: [Move](../../-move/index.md), chess960: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))<br>move() converts a Move to a string in coordinate notation (g1f3, a7a8q). The only special case is castling where the e1g1 notation is printed in standard chess mode and in e1h1 notation it is printed in Chess960 mode. Internally, all castling moves are always encoded as 'king captures rook'. |

## Properties

| Name | Summary |
|---|---|
| [StartFEN](-start-f-e-n.md) | [jvm]<br>const val [StartFEN](-start-f-e-n.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
