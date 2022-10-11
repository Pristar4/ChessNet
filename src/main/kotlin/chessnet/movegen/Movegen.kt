package chessnet.movegen

import chessnet.*

class Movegen {
    enum class GenType{
        CAPTURES,
        QUIETS,
        QUIET_CHECKS,
        EVASIONS,
        NON_EVASIONS,
        LEGAL,
    }
    companion object {


        fun <Type:GenType> generate(pos: Position, moveList: List<Move>): List<Move> {
            var us : Color = pos.sideToMove
            generateAll<Color, Type>(pos, moveList)


            return moveList

        }


        fun <Us:Color, Pt:PieceType,Checks:Boolean> generateMoves(pos: Position, moveList: List<Move>, target:Bitboard){


        }
        fun <Us:Color, Type:GenType> generateAll(pos:Position, moveList:  List<Move>): List<Move> {
            //TODO: implement
            return moveList

        }

        }
    }





