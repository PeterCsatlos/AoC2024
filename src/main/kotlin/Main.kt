import java.util.LinkedList

fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val line = LinkedList<Long>()
    line.addAll(lines[0].split(' ').map { it.toLong() })

    for (blink in 0..<25) {
        var index = 0
        while (index < line.size) {
            val current = line[index]
            if (current == 0.toLong()) {
                line[index] = 1
            } else if (current.toString().length % 2 == 0) {
                line[index] = current.toString().substring(0,(current.toString().length/2)).toLong()
                line.add(index+1, current.toString().substring(current.toString().length/2).toLong())
                index++
            } else {
                line[index] = current * 2024
            }
            index++
        }
    }
    println(line.size)
}

