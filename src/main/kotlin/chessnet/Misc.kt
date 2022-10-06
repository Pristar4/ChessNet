package chessnet

const val ENGINE_NAME = "ChessNet"
const val ENGINE_AUTHOR = "Felix Jung"
const val ENGINE_VERSION = "0.0.1"

// get version from build.gradle.kts
fun engineInfo(): String {
    //  get current month day year
    val date = java.time.LocalDate.now()

    return "$ENGINE_NAME v$ENGINE_VERSION  $date by $ENGINE_AUTHOR"
}
