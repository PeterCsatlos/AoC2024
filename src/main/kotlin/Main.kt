import kotlin.math.abs

fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()
    safeCount(lines)
}

private fun safeCount(lines: List<String>) {
    var safeCount = 0

    lines.forEach { line ->

        val values = line.split(" ")
        var deletedIndex = 0
        var isSafe = isSafeCheck(values)
        while (!isSafe && deletedIndex < values.size) {
            isSafe = isSafeCheck(values.filterIndexed { index, _ -> index != deletedIndex })
            deletedIndex++
        }

        if (isSafe) {
            safeCount++
        }
    }
    println(safeCount)
}

private fun isSafeCheck(values: List<String>): Boolean {
    var isSafe = true
    var previous = values[0].toInt()
    val direction = values[1].toInt() < values[0].toInt()
    values.subList(1, values.size).forEach { value ->
        val intValue = value.toInt()
        if (abs(previous - intValue) > 3 || abs(previous - intValue) < 1) {
            isSafe = false
        }
        if (direction && intValue > previous) {
            isSafe = false
        } else if (!direction && intValue < previous) {
            isSafe = false
        }
        previous = intValue
    }
    return isSafe
}
