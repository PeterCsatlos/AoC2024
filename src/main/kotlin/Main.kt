import kotlin.math.abs

fun main(args: Array<String>) {
    val lines = readInput(args)
    distanceBetweenLists(lines ?: emptyList())
    similarityCheck(lines ?: emptyList())
}

private fun readInput(args: Array<String>): List<String>? {
    return object {}.javaClass.getResourceAsStream(args[0])?.bufferedReader()?.readLines()
}

fun distanceBetweenLists(lines: List<String>) {
    val lefts = lines.map { it.split("  ")[0] }
    val rights = lines.map { it.split("  ")[1] }

    val leftOrdered = lefts.sorted()
    val rigthOrdered = rights.sorted()

    println("Distance: " + leftOrdered.zip(rigthOrdered).map { (left, right) ->
        abs(left.toFloat() - right.toFloat())
    }.sum())
}


fun similarityCheck(lines: List<String>) {
    val lefts = lines.map { it.split("  ")[0] }
    val rights = lines.map { it.split("  ")[1] }

    val mapOfAppearance: Map<Double, Int> = rights.groupingBy { it.toDouble() }.eachCount()

    println(
        lefts.sumOf {
            ((mapOfAppearance[it.toDouble()]?.toDouble() ?: 0.0) * it.toDouble())
        }
    )
}
