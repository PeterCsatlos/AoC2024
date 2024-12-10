fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val matrix = Array(lines.size) { rowindex -> lines[rowindex].toCharArray() }

    val zeros = mutableListOf<Pair<Int,Int>>()

    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            if (char != '.') {
                antennas.computeIfAbsent(char) { mutableListOf() }.add(row to col)
            }
        }
    }

    antennas.entries.removeIf { (_, positions) -> positions.size == 1 }


    matrix.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            if (char == '0') {
                zeros.add(Pair(lineIndex, charIndex))
            }

        }
    }
    val crossMatrix = createCrossMatrix(matrix)

    antennas.forEach { (_, positions) ->
        var i = 0
        while (i< positions.size-1) {
            var j = i+1
            while (j < positions.size) {
                val first = positions[i]
                val second = positions[j]
                crossMatrix[first.first][first.second] = "#"
                crossMatrix[second.first][second.second] = "#"

                val xleap1 = first.first - second.first // -1
                val yleap1 = first.second - second.second // 3
                var step = first
                addEveryValidAntinode(step, xleap1, yleap1, matrix, crossMatrix)

                val xleap2 = second.first - first.first // 1
                val yleap2 = second.second - first.second // -3
                step = second
                addEveryValidAntinode(step, xleap2, yleap2, matrix, crossMatrix)

                j++
            }
            i++
        }
    }

    var result = 0
    crossMatrix.forEach { crossLine ->
        crossLine.forEach {
            if (it == "#") {
                result++
            }
        }
    }

    println(result)
}

private fun addEveryValidAntinode(
    step: Pair<Int, Int>,
    xleap1: Int,
    yleap1: Int,
    matrix: Array<CharArray>,
    crossMatrix: Array<MutableList<String>>
) {
    var end = false
    var step1 = step
    while (!end) {
        val antinode = Pair(step1.first + xleap1, step1.second + yleap1)
        step1 = antinode
        if (antinode.first >= 0 && antinode.first < matrix.size && antinode.second >= 0 && antinode.second < matrix[0].size) {
            crossMatrix[antinode.first][antinode.second] = "#"
        } else {
            end = true
        }
    }
}

fun createCrossMatrix(matrix: Array<CharArray>): Array<MutableList<String>> {
    return Array(matrix.size) { rowIndex ->
        matrix[rowIndex].copyOf().map { it.toString() }.toMutableList()
    }
}
