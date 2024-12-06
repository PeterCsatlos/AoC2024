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
    while (index.first >= 0 && index.first <= matrix.size-1 && index.second >= 0 && index.second <= matrix[0].size-1) {
        matrix[index.first][index.second] = 'X'
        if (movement == '^') {
            if (index.first == 0) {
                index = Pair(-1, -1)
            } else {
                if (matrix[index.first - 1][index.second] == '#') {
                    movement = '>'
                } else {
                    index = Pair(index.first - 1, index.second)
                }
            }
        } else if (movement == '>') {
            if (index.second == matrix[0].size-1) {
                index = Pair(-1, matrix[0].size + 1)
            } else {
                if (matrix[index.first][index.second + 1] == '#') {
                    movement = 'v'
                } else {
                    index = Pair(index.first, index.second + 1)
                }
            }
        } else if (movement == 'v') {
            if (index.first == matrix.size-1) {
                index = Pair(matrix.size + 1, matrix[0].size + 1)
            } else {
                if (matrix[index.first + 1][index.second] == '#') {
                    movement = '<'
                } else {
                    index = Pair(index.first + 1, index.second)
                }
            }
        } else if (movement == '<') {
            if (index.second == 0) {
                index = Pair(-1, -1)
            } else {
            if (matrix[index.first][index.second - 1] == '#') {
                movement = '^'
            } else {
                index = Pair(index.first, index.second - 1)
            }}
        }
    }

    var count = 0

    matrix.forEach { line ->
        count += line.count { char -> char == 'X' }
    }

    println(count)
}
