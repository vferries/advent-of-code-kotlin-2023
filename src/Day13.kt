fun main() {
    val input = readInput("day13")
    summarizeNotes(parseMirrors(input)).println()
    summarizeWithSmudge(parseMirrors(input)).println()
}

fun summarizeWithSmudge(maps: List<List<String>>): Int {
    return maps.sumOf {
        val oldSymmetries = findHorizontalSymmetry(it) to findVerticalSymmetry(it)
        var newSymmetries = listOf<Int>() to listOf<Int>()
        var smudgeIndex = 0
        while (newSymmetries.first.isEmpty() && newSymmetries.second.isEmpty() || newSymmetries == oldSymmetries) {
            val (row, col) = smudgeIndex / it[0].length to smudgeIndex % it[0].length
            val modifiedMap = it.mapIndexed { i, line ->
                if (i == row) {
                    val switched = when (line[col]) {
                        '.' -> "#"
                        '#' -> "."
                        else -> throw IllegalArgumentException("unknown char")
                    }
                    line.replaceRange(col..col, switched)
                } else {
                    line
                }
            }
            newSymmetries = findHorizontalSymmetry(modifiedMap) to findVerticalSymmetry(modifiedMap)
            smudgeIndex++
        }
        100 * (newSymmetries.first - oldSymmetries.first.toSet()).sum() + (newSymmetries.second - oldSymmetries.second.toSet()).sum()
    }
}

fun summarizeNotes(maps: List<List<String>>): Int =
    maps.sumOf { 100 * findHorizontalSymmetry(it).sum() + findVerticalSymmetry(it).sum() }

fun parseMirrors(input: List<String>): List<List<String>> =
    input.joinToString("\n").split("\n\n").map { it.lines() }

fun findVerticalSymmetry(lines: List<String>): List<Int> {
    val transposed = lines[0].indices.map { i -> lines.map { it[i] }.joinToString("") }
    return findHorizontalSymmetry(transposed)
}

fun findHorizontalSymmetry(lines: List<String>): List<Int> {
    val symmetries = mutableListOf<Int>()
    for (i in 1..lines.lastIndex) {
        if (lines.take(i).reversed().zip(lines.drop(i)).all { (l1, l2) -> l1 == l2 }) {
            symmetries += i
        }
    }
    return symmetries
}
