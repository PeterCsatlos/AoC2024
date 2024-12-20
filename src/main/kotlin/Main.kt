import java.util.*

fun main() {
    val map = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()
    println("Total price: ${calculateTotalPrice(map)}")
}

fun calculateTotalPrice(map: List<String>): Int {
    val rows = map.size
    val cols = map[0].length
    val visited = Array(rows) { BooleanArray(cols) }
    var totalPrice = 0

    fun calculateMeasures(startRow: Int, startCol: Int, plant: Char): Pair<Int, Int> {
        val directions = listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(Pair(startRow, startCol))
        visited[startRow][startCol] = true

        var area = 0
        var perimeter = 0

        while (queue.isNotEmpty()) {
            val (row, column) = queue.poll()
            area++

            for ((xChange, yChange) in directions) {
                val newRow = row + xChange
                val newColumn = column + yChange


                if (newRow in 0..<rows && newColumn in 0..<cols) {
                    if (map[newRow][newColumn] == plant && !visited[newRow][newColumn]) {
                        visited[newRow][newColumn] = true
                        queue.add(Pair(newRow, newColumn))
                    } else if (map[newRow][newColumn] != plant) {
                        perimeter++
                    }
                } else {
                    perimeter++
                }
            }
        }
        return Pair(area, perimeter)
    }

    for (row in 0..<rows) {
        for (col in 0..<cols) {
            if (!visited[row][col]) {
                val plant = map[row][col]
                val (area, perimeter) = calculateMeasures(row, col, plant)
                totalPrice += area * perimeter
            }
        }
    }

    return totalPrice
}
