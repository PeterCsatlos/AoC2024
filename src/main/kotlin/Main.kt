import kotlin.math.abs

fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()
    safeCount(lines)
}

private fun safeCount(lines: List<String>) {
    var safeCount = 0

    lines.forEach { line ->
        var isSafe = true

        val values = line.split(" ")
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

        if (isSafe) {
            safeCount++
        }
    }
    println(safeCount)
}
