fun main() {
    val input = readInput("day21")
    reachableGardenPlots(input, 65 to 65, 64).println()
    reachableGardenPlots(input).println()
}

fun reachableGardenPlots(input: List<String>): Long {
    val steps = 26501365
    val l = input.size
    val evenGrids = reachableGardenPlots(input, l / 2 to l / 2, 2 * l, 0)
    val oddGrids = reachableGardenPlots(input, l / 2 to l / 2, 2 * l + 1, 1)
    val innerFull = (steps / l - 1).toLong()
    val right = reachableGardenPlots(input, l / 2 to 0, l - 1, 0)
    val top = reachableGardenPlots(input, l - 1 to l / 2, l - 1, 0)
    val left = reachableGardenPlots(input, l / 2 to l - 1, l - 1, 0)
    val bottom = reachableGardenPlots(input, 0 to l / 2, l - 1, 0)
    val topLeftExt = reachableGardenPlots(input, l - 1 to l - 1, l / 2 - 1, 0)
    val topLeftInt = reachableGardenPlots(input, l - 1 to l - 1, l * 3 / 2 - 1, 1)
    val bottomLeftExt = reachableGardenPlots(input, 0 to l - 1, l / 2 - 1, 0)
    val bottomLeftInt = reachableGardenPlots(input, 0 to l - 1, l * 3 / 2 - 1, 1)
    val topRightExt = reachableGardenPlots(input, l - 1 to 0, l / 2 - 1, 0)
    val topRightInt = reachableGardenPlots(input, l - 1 to 0, l * 3 / 2 - 1, 1)
    val bottomRightExt = reachableGardenPlots(input, 0 to 0, l / 2 - 1, 0)
    val bottomRightInt = reachableGardenPlots(input, 0 to 0, l * 3 / 2 - 1, 1)
    val oddCount = (innerFull / 2 * 2 + 1) * (innerFull / 2 * 2 + 1)
    val evenCount = ((innerFull + 1) / 2 * 2) * ((innerFull + 1) / 2 * 2)
    return evenCount * evenGrids +
            oddCount * oddGrids +
            right + top + left + bottom +
            (innerFull + 1) * (topLeftExt + bottomLeftExt + topRightExt + bottomRightExt) +
            innerFull * (topLeftInt + bottomLeftInt + topRightInt + bottomRightInt)
}

fun reachableGardenPlots(input: List<String>, startingPosition: Position, steps: Int, parity: Int = 0): Long {
    val reachable = input.indices.flatMap { row -> input[row].mapIndexed { col, value -> Triple(row, col, value) } }
        .filter { it.third != '#' }
        .map { it.first to it.second }.toSet()
    val visited = mutableMapOf(startingPosition to 0)
    var toVisit = listOf(startingPosition)
    repeat(steps) { step ->
        val next =
            toVisit.flatMap { it.neighbors }.toSet().filter { reachable.contains(it) && !visited.containsKey(it) }
        toVisit = next
        visited.putAll(next.map { it to step + 1 })
    }
    return visited.filter { it.value % 2 == parity }.size.toLong()
}
