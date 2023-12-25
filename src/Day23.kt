import kotlin.math.max


fun main() {
    val input = readInput("day23")
    longestScenicRoute(input).println()
    longestScenicRouteWithoutSlopes(input).println()
}

fun longestScenicRoute(input: List<String>): Int {
    val visitablePositions = input.flatMapIndexed { row, line -> line.mapIndexed { col, c -> (row to col) to c } }
        .filter { it.second != '#' }.toMap()
    val neighbors = visitablePositions.map { (pos, c) ->
        val set: Set<Position> = when (c) {
            'v' -> setOf(pos.first + 1 to pos.second)
            '>' -> setOf(pos.first to pos.second + 1)
            else -> pos.neighbors.filter { visitablePositions.containsKey(it) }.toSet()
        }
        pos to set
    }.toMap()
    val startingPosition = visitablePositions.keys.find { it.first == 0 }!!
    val endingPosition = visitablePositions.keys.find { it.first == input.lastIndex }!!

    var longest = 0
    val toVisit = mutableListOf(startingPosition to setOf(startingPosition))
    while (toVisit.isNotEmpty()) {
        val (position, visited) = toVisit.removeFirst()
        if (position == endingPosition) {
            longest = max(longest, visited.size)
        }
        toVisit.addAll(neighbors.getValue(position).filter { !visited.contains(it) }.map { it to visited + it })
    }
    return longest - 1
}

fun longestScenicRouteWithoutSlopes(input: List<String>): Int {
    val visitablePositions = input.flatMapIndexed { row, line -> line.mapIndexed { col, c -> (row to col) to c } }
        .filter { (_, c) -> c != '#' }.map { it.first }.toSet()
    val start = visitablePositions.single { (row, _) -> row == 0 }
    val end = visitablePositions.single { (row, _) -> row == input.lastIndex }
    val vertices = findVertices(start, end, visitablePositions)
    val edges = findEdges(vertices, visitablePositions)
    return longestPath(start, end, edges)!!
}

private fun longestPath(
    start: Position,
    end: Position,
    edges: Map<Position, MutableList<Pair<Position, Int>>>,
    cost: Int = 0,
    visited: MutableSet<Position> = mutableSetOf(),
): Int? {
    val neighbors = edges.getValue(start).filter { !visited.contains(it.first) }
    if (start == end) {
        return cost
    }
    visited += start
    var best: Int? = null
    for ((neighbor, edgeCost) in neighbors) {
        val longestPath = longestPath(neighbor, end, edges, cost + edgeCost, visited)
        if (longestPath != null && (best == null || longestPath > best))
            best = longestPath
    }
    visited -= start
    return best
}

private fun findEdges(
    vertices: MutableSet<Position>,
    visitablePositions: Set<Position>
): Map<Position, MutableList<Pair<Position, Int>>> {
    val edges = vertices.associateWith { mutableListOf<Pair<Position, Int>>() }
    for (vertex in vertices) {
        val visited = mutableSetOf<Position>()
        val toVisit = mutableListOf(vertex to 0)
        while (toVisit.isNotEmpty()) {
            val (next, steps) = toVisit.removeFirst()
            if (visited.contains(next)) {
                continue
            }
            visited += next
            if (next != vertex && vertices.contains(next)) {
                edges.getValue(vertex).add(next to steps)
            } else {
                toVisit.addAll(next.neighbors.filter { visitablePositions.contains(it) }.map { it to steps + 1 })
            }
        }
    }
    return edges
}

private fun findVertices(
    start: Position,
    end: Position,
    visitablePositions: Set<Position>
): MutableSet<Position> {
    val vertices = mutableSetOf(start, end)
    for (pos in visitablePositions) {
        val validNeighbors = pos.neighbors.count { n -> visitablePositions.contains(n) }
        if (validNeighbors > 2) {
            vertices += pos
        }
    }
    return vertices
}
