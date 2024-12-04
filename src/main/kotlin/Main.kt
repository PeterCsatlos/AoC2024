fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val matrix = Array(lines.size) { rowindex -> lines[rowindex].toCharArray() }

    var xMasCount = 0

    for ((i,row) in matrix.withIndex()) {
        for ((j, column) in row.withIndex()) {
            if (column == 'A') {
                var iLeap = i - 1
                var jLeap = j - 1
                if (indexesCheck(iLeap, matrix, jLeap, row) && (matrix[iLeap][jLeap] == 'M' || matrix[iLeap][jLeap] == 'S')) {

                    if(indexesCheck(i+1, matrix, j+1, row) &&
                        (
                                (matrix[iLeap][jLeap] == 'M' && matrix[i+1][j+1] == 'S') ||
                                (matrix[iLeap][jLeap] == 'S' && matrix[i+1][j+1] == 'M'))
                        ) {
                        iLeap = i - 1
                        jLeap = j + 1
                        if (indexesCheck(
                                iLeap,
                                matrix,
                                jLeap,
                                row
                            ) && (matrix[iLeap][jLeap] == 'M' || matrix[iLeap][jLeap] == 'S')
                        ) {

                            if (indexesCheck(i+1, matrix, j-1, row) &&
                                (
                                        (matrix[iLeap][jLeap] == 'M' && matrix[i + 1][j - 1] == 'S') ||
                                                (matrix[iLeap][jLeap] == 'S' && matrix[i + 1][j - 1] == 'M'))
                            ) {
                                xMasCount++
                            }
                        }
                    }
                }
            }
        }
    }

    println(xMasCount)

// countXmas(matrix)

}

private fun countXmas(matrix: Array<CharArray>) {
    var xmasCount = 0

    for ((i,row) in matrix.withIndex()) {
        for ((j, column) in row.withIndex()) {
            if (column == 'X') {
                for (istep in -1..1) {
                    for (jstep in -1..1) {
                        var iLeap = i + istep
                        var jLeap = j + jstep
                        if (indexesCheck(iLeap, matrix, jLeap, row) && matrix[iLeap][jLeap] == 'M') {
                            iLeap = iLeap + istep
                            jLeap = jLeap + jstep
                            if( indexesCheck(iLeap, matrix, jLeap, row) && matrix[iLeap][jLeap] == 'A') {
                                iLeap = iLeap + istep
                                jLeap = jLeap + jstep
                                if(indexesCheck(iLeap, matrix, jLeap, row) && matrix[iLeap][jLeap] == 'S') {
                                    xmasCount++
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    println(xmasCount)
}

private fun indexesCheck(
    i: Int,
    matrix: Array<CharArray>,
    j: Int,
    row: CharArray
) = i >= 0 && i  < matrix.size && j >= 0 && j < row.size
