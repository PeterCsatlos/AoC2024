fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val rules = lines.subList(0, lines.indexOfFirst { it == "" }).map { Pair(it.split('|')[0], it.split('|')[1]) }
    val order = lines.subList(lines.indexOfFirst { it == "" } + 1, lines.size)

    var sum = 0
    order.forEach { orderString ->
        val orders = orderString.split(',')
        val valid = validityCheck(orders, rules)

        if (!valid) {
            val ordersMut = orderString.split(',').toMutableList()
            var i = 0
            var j = ordersMut.size-1
            while (i < ordersMut.size) {
                while (j > i) {
                    if (rules.any { it == Pair(ordersMut[j], ordersMut[i]) }) {
                        ordersMut.moveElement(i, j)
                        j = ordersMut.size-1
                        i = 0
                    }
                    j--
                }
                i++
                j = ordersMut.size-1
            }
            sum += ordersMut[ordersMut.size / 2].toInt()
        }
    }
    println(sum)
}

private fun validityCheck(
    orders: List<String>,
    rules: List<Pair<String, String>>
): Boolean {
    var valid = true
    for (i in 0..orders.size - 2) {
        if (valid) {
            for (j in i + 1..<orders.size) {
                if (valid && rules.any { it == Pair(orders[j], orders[i]) }) {
                    valid = false
                }
            }
        }
    }
    return valid
}

fun <T> MutableList<T>.moveElement(fromIndex: Int, toIndex: Int) {
    if (fromIndex in indices && toIndex in indices) {
        val element = removeAt(fromIndex)
        add(toIndex, element)
    } else {
        throw IndexOutOfBoundsException("Invalid indices: fromIndex=$fromIndex, toIndex=$toIndex")
    }
}
