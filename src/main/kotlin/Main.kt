import kotlin.math.pow

fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    val line = mutableListOf<Long>()
    line.addAll(lines[0].split(' ').map { it.toLong() })
    val stonesToTimesToOutcome: MutableMap<Long, MutableMap<Int, Long>> = mutableMapOf()
    val res = line.sumOf { stone ->
        recursive(stone, 75, stonesToTimesToOutcome)
    }
    println(res)

}

fun recursive(value: Long, times: Int, stonesToTimesToOutcome: MutableMap<Long, MutableMap<Int, Long>>): Long {
    if (times==0) {
        return 1
    }

    stonesToTimesToOutcome[value]?.get(times)?.let {
        return it
    }

    val digits = (Math.log10(value.toDouble()).toInt() + 1)
    val length = if (value == 0L) 1 else digits

    val outcome = when {
        value == 0L -> recursive(1, times - 1, stonesToTimesToOutcome)

        length % 2 == 0 -> splitLong(value, digits).sumOf { recursive(it, times - 1, stonesToTimesToOutcome) }
        else -> recursive(value * 2024L, times - 1, stonesToTimesToOutcome)
    }

    stonesToTimesToOutcome.getOrPut(value) { mutableMapOf() }[times] = outcome
    return outcome
}

fun splitLong(number: Long, digits: Int): Sequence<Long> {
    val halfDigits = digits / 2
    val divisor = 10.0.pow(halfDigits).toLong() // Power of 10 for splitting
    val left = number / divisor // Left half
    val right = number % divisor // Right half
    return sequenceOf(left, right)
}
