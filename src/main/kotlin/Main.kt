fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    var sum = 0L
    val regex = Regex("don't\\(\\).*?do\\(\\)")
    val adjustedLines = lines.joinToString("").replace(regex, "")

    val stack = ArrayDeque<Char>()
    val firstNumberStack = ArrayDeque<String>()
    val secondNumberStack = ArrayDeque<String>()
    val conditionalStack = ArrayDeque<String>()

    for (char in adjustedLines) {
        if (stack.isEmpty() && char == 'm') {
            stack.addLast(char)
        } else if (char == 'u' && stack.isNotEmpty() && stack.last() == 'm') {
            stack.addLast(char)
        } else if (char == 'l' && stack.isNotEmpty() && stack.last() == 'u') {
            stack.addLast(char)
        } else if (char == '(' && stack.isNotEmpty() && stack.last() == 'l') {
            stack.addLast(char)
        } else if (char.isDigit() && firstNumberStack.size < 3 && stack.isNotEmpty() && stack.last() == '(') {
            firstNumberStack.addLast(char.toString())
        } else if (char == ',' && firstNumberStack.isNotEmpty()) {
            stack.addLast(char)
        } else if (char.isDigit() && secondNumberStack.size < 3 && stack.isNotEmpty() && stack.last() == ',') {
            secondNumberStack.addLast(char.toString())
        } else if (char == ')' && stack.isNotEmpty() && secondNumberStack.isNotEmpty()) {
            stack.addLast(char)
        } else {
            clearStacks(stack, firstNumberStack, secondNumberStack)
        }

        if (firstNumberStack.isNotEmpty() && secondNumberStack.isNotEmpty() && stack.last() == ')' && conditionalStack.toArray().joinToString("") != "don't()") {
            sum += firstNumberStack.toArray().joinToString("").toInt() * secondNumberStack.toArray()
                .joinToString("").toInt()
            clearStacks(stack, firstNumberStack, secondNumberStack)
        }

    }
    println(sum)
}

fun clearStacks(
    stack: ArrayDeque<Char>,
    firstNumberStack: ArrayDeque<String>,
    secondNumberStack: ArrayDeque<String>
) {
    stack.removeAll(stack)
    firstNumberStack.removeAll(firstNumberStack)
    secondNumberStack.removeAll(secondNumberStack)
}
