import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day11")
    lengthOfShortestPaths(input).println()
    // < 840989653833
    lengthOfShortestPaths(input, 1_000_000).println()
}
fun lengthOfShortestPaths(input: List<String>, multiplier: Int = 2): Long {
    val galaxies = mutableListOf<Position>()
    for (row in input.indices) {
        for (col in input[row].indices) {
            if (input[row][col] == '#') {
                galaxies.add(row to col)
            }
        }
    }
    val emptyCols = input[0].indices.filter { col -> !input.map { it[col] }.joinToString("").contains("#") }
    val emptyRows = input.indices.filter { row -> !input[row].contains("#") }
    var total = 0L
    for (i in galaxies.indices) {
        for (j in i + 1..galaxies.lastIndex) {
            //add dist between galaxies[i] and galaxies[j]
            val (r1, c1) = galaxies[i]
            val (r2, c2) = galaxies[j]
            val dist = abs(r1 - r2) + abs(c1 - c2)
            val expandedRows = emptyRows.count { it in min(r1, r2)..max(r1, r2) }
            val expandedCols = emptyCols.count { it in min(c1, c2)..max(c1, c2) }
            total += dist + expandedCols * (multiplier - 1) + expandedRows * (multiplier - 1)
        }
    }
    return total
}
