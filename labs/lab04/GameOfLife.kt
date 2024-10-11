import kotlin.random.Random

val initialBoardArray = Array(10) { Array(10) { 0 } }
val newBoardArray = Array(10) { Array(10) { 0 } }

fun main() {
    initializeBoardWithRandomOneOrZeros()

    // Accessing the top-level variable inside the main function
    println("Initial Board:")
    prettyPrint2DArray(initialBoardArray)
    println("")
    println("Actual Board State:")
    updateBoardState(initialBoardArray, newBoardArray)
    prettyPrint2DArray(newBoardArray)
}

private fun initializeBoardWithRandomOneOrZeros() {
    for (i in initialBoardArray.indices) { // Loop over the rows
        for (j in initialBoardArray[i].indices) {
            initialBoardArray[i][j] = assignRandomValue()
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

private fun updateBoardState(previousBoard: Array<Array<Int>>, newBoard: Array<Array<Int>>) {
    for (i in previousBoard.indices) {
        for (j in previousBoard[i].indices) {
            var numOfNeighbours: Int = getNeighborCount(previousBoard, i, j)
            if (numOfNeighbours == 2 && previousBoard[i][j] == 1 || numOfNeighbours == 3) {
                newBoard[i][j] = 1
            } else {
                newBoard[i][j] = 0
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

