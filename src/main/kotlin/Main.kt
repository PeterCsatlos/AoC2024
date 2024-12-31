fun main(args: Array<String>) {
    val map = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val robots = mutableListOf<Robot>()

    map.forEach {line ->
        val posAndSpeed = line.split(" ")
        val pos = posAndSpeed[0].split("=")[1].split(",").let { Pair(it[0].toInt(), it[1].toInt()) }
        val speed = posAndSpeed[1].split("=")[1].split(",").let { Pair(it[0].toInt(), it[1].toInt()) }

        robots.add(Robot(x = pos.first, y = pos.second, speed = speed))
    }

    var i = 0
    do {
        robots.forEach{
            it.move()
        }
        i++
    } while (robots.map { Pair(it.x, it.y)}.toSet().size != robots.size)

    robots.print()
    println(i)
}

private const val X_MAX = 101
private const val Y_MAX = 103

fun List<Robot>.print() {
    val charArray = Array(X_MAX) { CharArray(Y_MAX) { '.' } }

    this.forEach { robot ->
        charArray[robot.x][robot.y] = '0'
    }

    charArray.forEach { row ->
        println(row.joinToString(""))
    }
}

class Robot(
    var x: Int,
    var y: Int,
    var speed: Pair<Int, Int>
) {
    fun move() {
        x = adjust(x+speed.first, X_MAX)

        y = adjust(y + speed.second, Y_MAX)
    }

    private fun adjust(coordinate: Int, max: Int): Int {
        if (coordinate >= max) {
            return coordinate - max
        } else if (coordinate < 0) {
            return max + coordinate
        }
        return coordinate
    }
}
