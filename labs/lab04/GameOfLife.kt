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
    println(getNeighborCount(boardArray, 3,4))
}

fun assignRandomValue(): Int {
    return Random.nextInt(0, 2)
}

fun gameOfLife(){

}
fun getCellValue(boardArray:Array<Array<Int>>, x: Int, y: Int): Int {
    return if (x in boardArray.indices && y in boardArray[x].indices) {
        boardArray[x][y]
    } else {
        0 // If the index is out of bounds, return 0
    }
}

fun getNeighborCount(boardArray: Array<Array<Int>>, currentFieldX: Int, currentFieldY: Int): Int {
// Check each of the neighboring cells
    var count = 0
    count += getCellValue(boardArray, currentFieldX - 1, currentFieldY)   // North
    count += getCellValue(boardArray, currentFieldX + 1, currentFieldY)   // South
    count += getCellValue(boardArray, currentFieldX, currentFieldY - 1)   // West
    count += getCellValue(boardArray, currentFieldX, currentFieldY + 1)   // East

    return count
}
