fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val rules = lines.subList(0,lines.indexOfFirst { it == "" }).map { Pair(it.split('|')[0],it.split('|')[1]) }
    val order = lines.subList(lines.indexOfFirst { it == "" }+1, lines.size)

    var sum = 0
    order.forEach {orderString ->
        val orders = orderString.split(',')
        var valid = true
        for (i in 0..orders.size-2) {
            if(valid) {
                for (j in i+1..<orders.size) {
                    if (valid && rules.any { it == Pair(orders[j], orders[i]) }) {
                        valid = false
                    }
                }
            }
        }

        if(valid) {
            sum += orders[orders.size/2].toInt()
        }
    }
    println(sum)
}
