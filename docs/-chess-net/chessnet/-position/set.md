//[ChessNet](../../../index.md)/[chessnet](../index.md)/[Position](index.md)/[set](set.md)

# set

[jvm]\
fun [set](set.md)(fenStr: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), isChess960: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), si: [StateInfo](../-state-info/index.md)): [Position](index.md)

[set](set.md) () initializes the position object with the given FEN string. This function is not very robust - make sure that input FENs are correct, this is assumed to be the responsibility of the GUI.
