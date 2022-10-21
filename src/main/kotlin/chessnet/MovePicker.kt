package chessnet

class MovePicker {
    var stage:Int = 0
    val pos = Position()
    val ttMove = Move()

    var moves = ExtMove(MAX_MOVES)
    enum class Stages(var value: Int = -1) {
        MAIN_TT, CAPTURE_INIT, GOOD_CAPTURE, REFUTATION, QUIET_INIT, QUIET, BAD_CAPTURE,
        EVASION_TT, EVASION_INIT, EVASION,
        PROBCUT_TT, PROBCUT_INIT, PROBCUT,
        QSEARCH_TT, QCAPTURE_INIT, QCAPTURE, QCHECK_INIT, QCHECK;



        init {
            if (value == -1) {
                value = ordinal
            }

        }
    }

    data class PickType(val value: Int) {
        companion object {
            val Next = PickType(0)
            val Best = PickType(1)
        }
    }

    // TODO: what does filter: (Move) -> Boolean mean?
    fun select(filter: (Move) -> Boolean, type: PickType): Move {
        return Move()
        //TODO: implement selection
    }

    /** MovePicker::next_move() is the most important method of the MovePicker class. It
     * returns a new pseudo-legal move every time it is called until there are no more
     * moves left, picking the move with the highest score from a list of generated moves.
     */
    fun nextMove(skipQuiets: Boolean = false):Move {
        //get the next move from the move list
        return Move()


    }
}