import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day10")
    computeFurtherDistance(input).println()
    countEnclosedTiles(input).println()
}

typealias Position = Pair<Int, Int>

val Position.neighbors: List<Position>
    get() = listOf(
        this.first + 1 to this.second,
        this.first - 1 to this.second,
        this.first to this.second + 1,
        this.first to this.second - 1
    )

val Position.neighborsWithDiagonals: List<Position>
    get() = listOf(
        this.first + 1 to this.second,
        this.first - 1 to this.second,
        this.first to this.second + 1,
        this.first to this.second - 1,
        this.first + 1 to this.second + 1,
        this.first - 1 to this.second - 1,
        this.first - 1 to this.second + 1,
        this.first + 1 to this.second - 1
    )

fun countEnclosedTiles(input: List<String>): Int {
    val mInput = input.toMutableList()
    val startingPosition = getStartingPosition(mInput)
    var nexts = startingPosition.neighbors.filter {
        val inMap = it.first >= 0 && it.first < mInput.size && it.second >= 0 && it.second < mInput[it.first].length
        inMap && getConnections(mInput, it).contains(startingPosition)
    }
    val newChar = when {//bottom, top, right, left
        nexts.contains(startingPosition.neighbors[0]) && nexts.contains(startingPosition.neighbors[2]) -> "F"
        nexts.contains(startingPosition.neighbors[0]) && nexts.contains(startingPosition.neighbors[3]) -> "7"
        nexts.contains(startingPosition.neighbors[1]) && nexts.contains(startingPosition.neighbors[3]) -> "J"
        nexts.contains(startingPosition.neighbors[1]) && nexts.contains(startingPosition.neighbors[2]) -> "L"
        nexts.contains(startingPosition.neighbors[2]) && nexts.contains(startingPosition.neighbors[3]) -> "-"
        nexts.contains(startingPosition.neighbors[0]) && nexts.contains(startingPosition.neighbors[1]) -> "|"
        else -> throw IllegalArgumentException("S cannot be replaced")
    }
    mInput[startingPosition.first] = mInput[startingPosition.first].replace("S", newChar)
    assert(nexts.size == 2)
    val mainLoop = mutableSetOf(startingPosition)
    while (nexts[0] != nexts[1]) {
        mainLoop.addAll(nexts)
        nexts =
            nexts.map { next -> getConnections(mInput, next).find { connection -> !mainLoop.contains(connection) }!! }
    }
    mainLoop.add(nexts[0])

    var enclosed = 0
    for (row in mInput.indices) {
        for (col in mInput[0].indices) {
            if (!mainLoop.contains(row to col)) {
                val rayCast = (row+1..mInput.lastIndex).filter { ri -> mainLoop.contains(ri to col) && mInput[ri][col] != '|' }.map { ri -> mInput[ri][col] }
                val rights = rayCast.count { it == 'F' || it == 'L' }
                val lefts = rayCast.count { it == 'J' || it == '7' }
                val dashes = rayCast.count { it == '-' }
                val leftRights = min(lefts, rights)
                if ((dashes + leftRights + ((max(lefts, rights) - leftRights) % 2)) % 2 == 1) {
                    enclosed++
                }
            }
        }
    }
    return enclosed
}


fun computeFurtherDistance(input: List<String>): Int {
    val startingPosition = getStartingPosition(input)
    var nexts = startingPosition.neighbors.filter {
        val inMap = it.first >= 0 && it.first < input.size && it.second >= 0 && it.second < input[it.first].length
        inMap && getConnections(input, it).contains(startingPosition)
    }
    assert(nexts.size == 2)
    val visited = mutableSetOf(startingPosition)
    var steps = 1
    while (nexts[0] != nexts[1]) {
        visited.addAll(nexts)
        nexts =
            nexts.map { next -> getConnections(input, next).find { connection -> !visited.contains(connection) }!! }
        steps++
    }
    return steps
}

fun getConnections(
    input: List<String>,
    it: Position
) = when (input[it.first][it.second]) {
    '|' -> listOf(
        it.first - 1 to it.second,
        it.first + 1 to it.second
    ) // is a vertical pipe connecting north and south.
    '-' -> listOf(
        it.first to it.second - 1,
        it.first to it.second + 1
    ) // is a horizontal pipe connecting east and west.
    'L' -> listOf(
        it.first - 1 to it.second,
        it.first to it.second + 1
    ) // is a 90-degree bend connecting north and east.
    'J' -> listOf(
        it.first - 1 to it.second,
        it.first to it.second - 1
    ) //is a 90-degree bend connecting north and west.
    '7' -> listOf(
        it.first + 1 to it.second,
        it.first to it.second - 1
    ) //is a 90-degree bend connecting south and west.
    'F' -> listOf(
        it.first + 1 to it.second,
        it.first to it.second + 1
    ) //is a 90-degree bend connecting south and east.
    else -> listOf()
}

fun getStartingPosition(input: List<String>): Pair<Int, Int> {
    var startingPosition = 0 to 0
    for (line in input.indices) {
        if (input[line].contains("S")) {
            startingPosition = line to input[line].indexOf('S')
            break
        }
    }
    return startingPosition
}
