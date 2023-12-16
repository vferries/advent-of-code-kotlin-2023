import Direction.*
import Reflector.BACKSLASH
import Reflector.SLASH
import Splitter.HORIZONTAL
import Splitter.VERTICAL

fun main() {
    val input = readInput("day16")
    countTilesEnergized(input).println()
    maxTilesEnergized(input).println()
}

fun maxTilesEnergized(input: List<String>): Int {
    val lastRow = input.lastIndex
    val lastCol = input[0].lastIndex
    return (input.indices.map { row -> Ray(row to 0, EAST) } +
            input.indices.map { row -> Ray(row to lastCol, WEST) } +
            input[0].indices.map { col -> Ray(0 to col, SOUTH) } +
            input[0].indices.map { col -> Ray(lastRow to col, NORTH) }).maxOfOrNull { countTilesEnergized(input, it) }
        ?: -1
}

fun countTilesEnergized(input: List<String>): Int = countTilesEnergized(input, Ray(0 to 0, EAST))

fun countTilesEnergized(input: List<String>, start: Ray): Int {
    val (rows, cols) = input.size to input[0].length
    val visited = mutableSetOf<Ray>()
    val toVisit = mutableListOf(start)
    while (toVisit.isNotEmpty()) {
        val head = toVisit.removeFirst()
        if (!head.position.inGrid(rows, cols) || visited.contains(head)) {
            continue
        } else {
            visited.add(head)
        }
        val pos = head.position
        val dir = head.direction
        when (input[pos.first][pos.second]) {
            '.' -> toVisit.add(head.forward())
            '-' -> {
                if (dir == EAST || dir == WEST) {
                    toVisit.add(head.forward())
                } else {
                    toVisit.addAll(head.split(HORIZONTAL))
                }
            }

            '|' -> {
                if (dir == NORTH || dir == SOUTH) {
                    toVisit.add(head.forward())
                } else {
                    toVisit.addAll(head.split(VERTICAL))
                }
            }

            '/' -> toVisit.add(head.reflect(SLASH))
            '\\' -> toVisit.add(head.reflect(BACKSLASH))
        }
    }
    return visited.map { it.position }.toSet().size
}

data class Ray(val position: Position, val direction: Direction) {
    fun forward(): Ray = Ray(
        when (direction) {
            NORTH -> position.first - 1 to position.second
            EAST -> position.first to position.second + 1
            SOUTH -> position.first + 1 to position.second
            WEST -> position.first to position.second - 1
        }, direction
    )

    fun reflect(reflector: Reflector): Ray {
        return Ray(
            position, when (reflector) {
                SLASH ->
                    when (direction) {
                        NORTH -> EAST
                        EAST -> NORTH
                        SOUTH -> WEST
                        WEST -> SOUTH
                    }

                BACKSLASH -> when (direction) {
                    NORTH -> WEST
                    EAST -> SOUTH
                    SOUTH -> EAST
                    WEST -> NORTH
                }
            }
        ).forward()
    }

    fun split(splitter: Splitter): List<Ray> {
        return when (splitter) {
            VERTICAL -> listOf(
                Ray(position, NORTH).forward(),
                Ray(position, SOUTH).forward()
            )

            HORIZONTAL -> listOf(
                Ray(position, EAST).forward(),
                Ray(position, WEST).forward()
            )
        }
    }
}

private fun Position.inGrid(rows: Int, cols: Int): Boolean =
    this.first in 0..<rows && this.second in 0..<cols

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

enum class Splitter {
    VERTICAL, HORIZONTAL
}

enum class Reflector {
    SLASH, BACKSLASH
}