fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val blocks = mutableListOf<String>()
    var isFile = true
    var index = 0
    lines[0].forEach {
        val current = it.toString().toInt()
        if (isFile) {
            for (i in 0..<current) {
                blocks.add(index.toString())
            }
            index++
        } else {
            for (i in 0 ..<current) {
                blocks.add(".")
            }
        }
        isFile = !isFile
    }
    val emptyIndexes = mutableListOf<Int>()
    blocks.forEachIndexed { charIndex, char ->
        if(char== ".") {
            emptyIndexes.add(charIndex)
        }
    }

    var end = blocks.size-1
    while (emptyIndexes.size > 0) {
        if (blocks.elementAt(end) == ".") {
            emptyIndexes.removeLast()
            end--
        } else {
            blocks[emptyIndexes[0]] = blocks.elementAt(end)
            blocks[end] = "."
            end--
            emptyIndexes.removeAt(0)
        }
    }

    var result = 0.toLong()
    index = 0
    while (index<= end) {
        result += blocks[index].toInt() * index
        index++
    }
    println(result)
}
