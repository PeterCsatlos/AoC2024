fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    var index: Pair<Int, Int> = Pair(1, 1)
    val matrix = Array(lines.size) { rowindex -> lines[rowindex].toCharArray() }

    matrix.forEachIndexed { lineIndex, line ->
        if (line.indexOf('^') != -1) {
            index = Pair(lineIndex, line.indexOf('^'))
        }
    }

    var movement = matrix[index.first][index.second]
    var count = 0
    matrix.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { charIndex, char ->
            if (char != '^' &&
                char != '>' &&
                char != '<' &&
                char != 'v' &&
                char != '#') {
                matrix[lineIndex][charIndex] = '#'
                val stuck = simulateMovement(index, matrix, movement)
                matrix[lineIndex][charIndex] = '.'
                if (stuck) {
                    count++
                    println(count)
                }
            }
        }
    }

    println(count)
}

private fun simulateMovement(
    initialIndex: Pair<Int, Int>,
    initialMatrix: Array<CharArray>,
    initialMovement: Char
): Boolean {
    var index = initialIndex
    var movement = initialMovement
    var stuck = false
    val matrix = deepCopyMatrix(initialMatrix)
    val crossMatrix = createCrossMatrix(initialMatrix)
    crossMatrix[initialIndex.first][initialIndex.second] = "."
    while (index.first > 0 && index.first < matrix.size - 1 && index.second > 0 && index.second < matrix[0].size - 1 && !stuck) {


        // make move
        //stuck = makeMove(matrix, index, crossMatrix, movement, stuck)
        if (crossMatrix[index.first][index.second].contains(movement)) {
            return true
        }

        if (movement == '^') {
            if (matrix[index.first - 1][index.second] == '#') {
                crossMatrix[index.first][index.second] = crossMatrix[index.first][index.second] + "^"
                movement = '>'
            } else {
                index = Pair(index.first - 1, index.second)
            }

        } else if (movement == '>') {
            if (matrix[index.first][index.second + 1] == '#')
            {
                crossMatrix[index.first][index.second] = crossMatrix[index.first][index.second] + ">"
                movement = 'v'
            } else {
                index = Pair(index.first, index.second + 1)
            }

        } else if (movement == 'v') {

            if (matrix[index.first + 1][index.second] == '#') {
                crossMatrix[index.first][index.second] = crossMatrix[index.first][index.second] + "v"
                movement = '<'
            } else {
                index = Pair(index.first + 1, index.second)
            }

        } else if (movement == '<') {
            if (matrix[index.first][index.second - 1] == '#') {
                crossMatrix[index.first][index.second] = crossMatrix[index.first][index.second] + "<"
                movement = '^'
            } else {
                index = Pair(index.first, index.second - 1)
            }

        }
    }
    return stuck
}

fun deepCopyMatrix(matrix: Array<CharArray>): Array<CharArray> {
    return Array(matrix.size) { rowIndex ->
        matrix[rowIndex].copyOf()
    }
}

fun createCrossMatrix(matrix: Array<CharArray>): Array<MutableList<String>> {
    return Array(matrix.size) { rowIndex ->
        matrix[rowIndex].copyOf().map { it.toString() }.toMutableList()
    }
}
