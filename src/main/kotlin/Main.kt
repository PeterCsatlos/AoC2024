fun main(args: Array<String>) {
    val map = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val robots = mutableListOf<Robot>()

    map.forEach {line ->
        val posAndSpeed = line.split(" ")
        val pos = posAndSpeed[0].split("=")[1].split(",").let { Pair(it[0].toInt(), it[1].toInt()) }
        val speed = posAndSpeed[1].split("=")[1].split(",").let { Pair(it[0].toInt(), it[1].toInt()) }

        robots.add(Robot(x = pos.first, y = pos.second, speed = speed))
    }

    robots.forEach{
        for (i in 0..<100) {
            it.move()
            println(i)
        }
    }

    val xHalf = X_MAX/2
    val yHalf = Y_MAX/2

    var first = 0L
    var second = 0L
    var third = 0L
    var fourth = 0L

    robots.forEach {
        if (it.x < xHalf && it.y < yHalf) {
            first++
        } else if (it.x > xHalf && it.y > yHalf) {
            fourth ++
        } else if (it.x < xHalf && it.y > yHalf) {
            third ++
        } else if (it.x > xHalf && it.y < yHalf) {
            second++
        }
    }

    println(first*second*third*fourth)
}

private const val X_MAX = 101
private const val Y_MAX = 103

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
