import kotlin.random.Random

val boardArray = Array(10) { Array(10) { 0 } }
val newBoardArray = Array(10) { Array(10) {0}}

fun main() {
    // Accessing the top-level variable inside the main function
    println(boardArray.contentDeepToString())

    for (i in boardArray.indices) { // Loop over the rows
        for (j in boardArray[i].indices) {
            boardArray[i][j] = assignRandomValue()
        }
    }
    println(boardArray.contentDeepToString())
}

fun assignRandomValue(): Int {
    return Random.nextInt(0, 2)
}