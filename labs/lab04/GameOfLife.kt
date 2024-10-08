import kotlin.random.Random

val boardArray = Array(10) { Array(10) { 0 } }
val newBoardArray = Array(10) { Array(10) { 0 } }

fun main() {
    initializeBoardWithRandomOneOrZeros()

    // Accessing the top-level variable inside the main function
    println("Initial Board:")
    prettyPrint2DArray(boardArray)
    println("Actual Board States:")
    prettyPrint2DArray(newBoardArray)

}

private fun initializeBoardWithRandomOneOrZeros() {
    for (i in boardArray.indices) { // Loop over the rows
        for (j in boardArray[i].indices) {
            boardArray[i][j] = assignRandomValue()
        }
    }
}

fun assignRandomValue(): Int {
    return Random.nextInt(0, 2)
}

fun prettyPrint2DArray(array: Array<Array<Int>>) {
    array.forEach { row ->
        println(row.joinToString(" ") { it.toString() })
    }
}

private fun updateBoardState() {
    for (i in boardArray.indices) { // Loop over the rows
        for (j in boardArray[i].indices) {
            var numOfNeighbours: Int = getNeighborCount(boardArray, i, j)

            if (numOfNeighbours < 2) {
                newBoardArray[i][j] = 0
            } else if (numOfNeighbours > 3) {
                newBoardArray[i][j] = 0
            } else {
                newBoardArray[i][j] = 1
            }
        }
    }
}

fun getCellValue(boardArray: Array<Array<Int>>, x: Int, y: Int): Int {
    return if (x in boardArray.indices && y in boardArray[x].indices) {
        boardArray[x][y]
    } else {
        0
    }
}

fun getNeighborCount(boardArray: Array<Array<Int>>, currentFieldX: Int, currentFieldY: Int): Int {
    var count = 0
    count += getCellValue(boardArray, currentFieldX - 1, currentFieldY)   // North
    count += getCellValue(boardArray, currentFieldX + 1, currentFieldY)   // South
    count += getCellValue(boardArray, currentFieldX, currentFieldY - 1)   // West
    count += getCellValue(boardArray, currentFieldX, currentFieldY + 1)   // East

    return count
}
