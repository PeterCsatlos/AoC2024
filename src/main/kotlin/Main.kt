import kotlin.math.pow

fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    var validResult = 0.toLong()
    var validCount = 0
    lines.forEach {line ->
        val result = line.split(':')[0].toLong()
        val equiasion = line.split(':')[1]

        val operands = equiasion.trim().split(' ')
        val bitNumber = operands.size-1
        var valid = false
        println("Operands: $operands")
        println("Result: $result")
        val possibleNumbers = generateBase3Numbers(bitNumber)
        var numberIndex = 0
        while (numberIndex < possibleNumbers.size && !valid) {
            val currentNumber = possibleNumbers[numberIndex]
            println("Currentnumber $currentNumber")
            var currentSum = operands[0].toLong()

            for (i in 0..<bitNumber) {
                if (currentNumber[i] == 0) {
                    currentSum += operands[i + 1].toLong()
                } else if (currentNumber[i] == 1) {
                    currentSum *= operands[i + 1].toLong()
                } else {
                    currentSum = "${currentSum}${operands[i+1]}".toLong()
                }
            }
            println("Currentsum: $currentSum")
            if (result == currentSum) {
                valid = true
                validCount++
                println("VALIDCOUNT $validCount")
                validResult += currentSum
                println("VALID NUMBER: $validResult")
            }
            numberIndex++
        }

        println(validResult)
    }
}

fun generateBase3Numbers(n: Int): List<MutableList<Int>> {
    val results = mutableListOf<MutableList<Int>>()
    val totalNumbers = 3.0.pow(n.toDouble()).toInt()

    for (i in 0..<totalNumbers) {
        results.add(toBase3AsList(i, n))
    }

    return results
}

// Convert an integer to its base-3 representation as a MutableList<Int>
fun toBase3AsList(number: Int, n: Int): MutableList<Int> {
    var num = number
    val base3 = mutableListOf<Int>()

    repeat(n) {
        base3.add(0, num % 3) // Prepend digits to maintain correct order
        num /= 3
    }

    // Ensure the list has exactly n elements
    while (base3.size < n) {
        base3.add(0, 0)
    }

    return base3
}
