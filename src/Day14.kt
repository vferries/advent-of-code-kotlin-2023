fun main() {
    val input = readInput("day14")
    computeTotalLoad(input).println()
    totalFinalLoad(input).println()
}

fun totalFinalLoad(input: List<String>): Int {
    val visited = mutableListOf<List<String>>()
    var next = input
    while (!visited.contains(next)) {
        visited.add(next)
        next = fullCycle(next)
    }
    val index = visited.indexOf(next)
    return computeScore(visited[((1_000_000_000 - index) % (visited.size - index)) + index])
}

fun fullCycle(input: List<String>): List<String> {
    var next = input
    repeat(4) {
        next = moveRocks(next)
        next = rotate(next)
    }
    return next
}

fun rotate(input: List<String>): List<String> = input[0].indices.map { col ->
    input.map { line -> line[col] }.joinToString("").reversed()
}

fun computeTotalLoad(input: List<String>): Int {
    val newTerrain = moveRocks(input)
    return computeScore(newTerrain)
}

fun computeScore(map: List<String>): Int {
    return map.reversed().mapIndexed { index, row -> row to index + 1 }
        .sumOf { (row, score) -> row.count { it == 'O' } * score }
}

fun moveRocks(input: List<String>): List<String> {
    val columns = input[0].indices.map { col ->
        var column = input.map { line -> line[col] }.joinToString("")
        var next = column.replace(".O", "O.")
        while (next != column) {
            column = next
            next = column.replace(".O", "O.")
        }
        column
    }
    return columns[0].indices.map { row ->
        columns.map { it[row] }.joinToString("")
    }
}
