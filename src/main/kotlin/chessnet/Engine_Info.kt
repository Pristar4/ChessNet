package chessnet

var Version = ""

fun engineInfo(toUci: Boolean = false): String {
    val name = "Chessnet"
    val author = "Felix Jung"
    println()

    val date = java.time.LocalDate.now()
        .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    if (Version.isEmpty()) Version = date.toString()
    //get  local date in format dd-mm-yyyy

    var info = (if (toUci) "\nid author " else "by ") + author
    return "$name $Version $info"

}