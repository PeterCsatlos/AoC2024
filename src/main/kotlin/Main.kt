fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val matrix = Array(lines.size) { rowindex -> lines[rowindex].toCharArray() }
    val nodeMatrix = createNodeMatrix(matrix)

    val zeros = mutableListOf<Pair<Int,Int>>()

    val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    lines.forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            if (char != '.') {
                antennas.computeIfAbsent(char) { mutableListOf() }.add(row to col)
            }
        }
    }

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

                val xleap1 = first.first - second.first // -1
                val yleap1 = first.second - second.second // 3
                val antinode1 = Pair(first.first + xleap1, first.second + yleap1)

                if (antinode1.first >=0 && antinode1.first < matrix.size && antinode1.second >= 0 && antinode1.second < matrix[0].size) {
                    crossMatrix[antinode1.first][antinode1.second] = "#"
                }

                val xleap2 = second.first - first.first // 1
                val yleap2 = second.second - first.second // -3
                val antinode2 = Pair(second.first+xleap2, second.second + yleap2 )

                if (antinode2.first >=0 && antinode2.first < matrix.size && antinode2.second >= 0 && antinode2.second < matrix[0].size) {
                    crossMatrix[antinode2.first][antinode2.second] = "#"
                }
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

fun createNodeMatrix(matrix: Array<CharArray>): Array<MutableList<String>> {
    return Array(matrix.size) { rowIndex ->
        matrix[rowIndex].copyOf().map { it.toString() }.toMutableList()
    }
}

fun createCrossMatrix(matrix: Array<CharArray>): Array<MutableList<String>> {
    return Array(matrix.size) { rowIndex ->
        matrix[rowIndex].copyOf().map { it.toString() }.toMutableList()
    }
}
