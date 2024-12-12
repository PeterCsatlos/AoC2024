import java.util.*

fun main(args: Array<String>) {
    val lines = object {}.javaClass.getResourceAsStream("input.txt")?.bufferedReader()?.readLines() ?: emptyList()

    // index to how big is the file, what is the content
    val files = TreeMap<Int,Pair<Int,Int>>()
    // index of the empty spaces where they start and for how long
    val emptyIndexes = TreeMap<Int, Int>()
    fillFilesAndEmptyIndexes(lines, files, emptyIndexes)
    val resultFiles = TreeMap(files)
    var filesIndex = files.size-1
    var emptyIndex = 0
    var found = false

    while (filesIndex > 0) {
        val file = files.entries.elementAt(filesIndex)
        //should be earlier then the file we are checking
        while (emptyIndex < emptyIndexes.size && emptyIndex < filesIndex && !found) {
            val empty = emptyIndexes.entries.elementAt(emptyIndex)
            if (empty.key > file.key ) {
                found = true
            } else if (empty.value >= file.value.first) {
                resultFiles.remove(file.key)
                resultFiles[empty.key] = Pair(file.value.first, file.value.second)
                val emptySpaceLeft = empty.value - file.value.first
                emptyIndexes[empty.key] = emptySpaceLeft
                if (emptySpaceLeft == 0) {
                    emptyIndexes.remove(empty.key)
                } else {
                    emptyIndexes[empty.key+file.value.first] = emptyIndexes[empty.key]!!
                    emptyIndexes.remove(empty.key)
                }
                found = true
            }
            emptyIndex++
        }
        found = false
        emptyIndex = 0
        filesIndex--
    }

    var result = 0.toLong()
    resultFiles.forEach { entry ->
        val startIndex = entry.key
        val length = entry.value.first
        val value = entry.value.second
        for (i in 0..<length) {
            result += (startIndex + i) * value
        }
    }
    println(result)
}

private fun fillFilesAndEmptyIndexes(
    lines: List<String>,
    files: TreeMap<Int, Pair<Int, Int>>,
    emptyIndexes: TreeMap<Int, Int>
): Int {
    var isFile = true
    var index = 0
    var fileNumber = 0
    lines[0].forEach { char ->
        val charAsInt = char.toString().toInt()
        if (isFile) {
            if (charAsInt != 0) {
                files[index] = Pair(charAsInt, fileNumber)
            }
            isFile = false
            fileNumber++
        } else {
            if (charAsInt != 0) {
                emptyIndexes[index] = charAsInt
            }
            isFile = true
        }
        index += char.toString().toInt()
    }
    return index
}
