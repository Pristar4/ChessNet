//[ChessNet](../../../index.md)/[chessnet](../index.md)/[Position](index.md)/[StateList](-state-list.md)

# StateList

[jvm]\
var [StateList](-state-list.md): [Deque](https://docs.oracle.com/javase/8/docs/api/java/util/Deque.html)&lt;[StateInfo](../-state-info/index.md)&gt;

A list to keep track of the position states along the setup moves( from the start position to the position just before the search starts). Needed by 'draw by repetition' detection. use deque because we need to be able to remove the last element
