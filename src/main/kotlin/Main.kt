fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines()
    val lefts = lines?.map { it.split("  ")[0] } ?: emptyList()
    val rights = lines?.map { it.split("  ")[1] } ?: emptyList()

    val leftOrdered = lefts.sorted()
    val rigthOrdered = rights.sorted()

   println(leftOrdered.zip(rigthOrdered).map { (left, right) ->
        Math.abs(left.toFloat() - right.toFloat())
    }.sum())
}
