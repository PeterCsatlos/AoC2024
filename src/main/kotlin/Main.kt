fun main() {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    var validResult = 0.toDouble()
    lines.forEach {line ->
        val result = line.split(':')[0].toDouble()
        val equiasion = line.split(':')[1]

        val operands = equiasion.trim().split(' ')
        val bitNumber = operands.size-1
        val binaryNumber = MutableList(bitNumber) { 0 }
        val maxBinaryNumber = MutableList(bitNumber) { 1 }
        var valid = false
        println("Initial Binary Number: ${binaryNumber.joinToString("")}")
        println("Operands: $operands")
        println("Result: $result")
        var max = false
        while (!max && !valid) {
            println("Increment ${binaryNumber.joinToString("")}")
            var currentSum = operands[0].toDouble()

            for (i in 0..<bitNumber) {
                if (binaryNumber[i] == 0) {
                    currentSum += operands[i + 1].toDouble()
                } else {
                    currentSum *= operands[i + 1].toDouble()
                }
            }
            if (result == currentSum) {
                valid = true
                validResult += currentSum
                println("VALID NUMBER: $validResult")
            }
            if (binaryNumber == maxBinaryNumber) {
                max = true
                println("MAX")
            }
            incrementBinary(binaryNumber)
        }

        println(validResult)
    }
}

fun incrementBinary(binaryNumber: MutableList<Int>) {
    var carry = 1
    for (i in binaryNumber.indices.reversed()) {
        val sum = binaryNumber[i] + carry
        binaryNumber[i] = sum % 2
        carry = sum / 2
        if (carry == 0) break
    }
}
