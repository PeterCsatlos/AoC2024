import kotlin.math.abs

fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream(args[0])?.bufferedReader()?.readLines()
    val lefts = lines?.map { it.split("  ")[0] } ?: emptyList()
    val rights = lines?.map { it.split("  ")[1] } ?: emptyList()

    val leftOrdered = lefts.sorted()
    val rigthOrdered = rights.sorted()

   println(leftOrdered.zip(rigthOrdered).map { (left, right) ->
        abs(left.toFloat() - right.toFloat())
    }.sum())
}
