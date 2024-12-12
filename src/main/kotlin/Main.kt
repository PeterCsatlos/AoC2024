fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val matrix = lines.map { row ->
        row.map { it.digitToInt() }.toIntArray()
    }.toTypedArray()

    // coordinates of the reached nines
    val reachedNines: HashSet<Pair<Int,Int>> = HashSet()

    val zeros = mutableListOf<Pair<Int, Int>>()
    for (rowIndex in matrix.indices) {
        for (colIndex in matrix[rowIndex].indices) {
            if (matrix[rowIndex][colIndex] == 0) {
                zeros.add(rowIndex to colIndex)
            }
        }
    }
    var result = 0
    zeros.forEach {(zeroX,zeroY) ->
        recursive(zeroX, zeroY, 0, matrix, reachedNines)
        result+= reachedNines.size
        reachedNines.removeAll(reachedNines)
    }

    println(result)

}

fun recursive(x: Int, y: Int, goal: Int, matrix: Array<IntArray>, reached: HashSet<Pair<Int,Int>>) {
    if(x<0 || y<0 || x== matrix.size || y==matrix[0].size) {
        println("Out of bounds")
        return
    }

    if(goal == 9 && matrix[x][y] == 9) {
        println("!!reached!!")
        reached.add( Pair(x,y))
    } else {
        if(matrix[x][y] != goal) {
            return
        } else {
            println("x: $x y: $y goal: $goal")
            recursive(x+1, y, goal+1, matrix, reached)
            recursive(x, y+1, goal+1, matrix, reached)
            recursive(x-1, y, goal+1, matrix, reached)
            recursive(x, y-1, goal+1, matrix, reached)
        }
    }
}
