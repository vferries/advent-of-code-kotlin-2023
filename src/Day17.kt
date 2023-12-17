import Direction.*
import java.util.*
import kotlin.math.min
import kotlin.reflect.KFunction3

fun main() {
    val input = readInput("day17")
    minimumHeatLoss(input).println()
    minimumUltraHeatLoss(input).println()
}

data class Crucible(val position: Position, val direction: Direction) {
    fun nextUltraMoves(score: Int, input: List<String>): Set<Pair<Int, Crucible>> {
        val moves = mutableSetOf<Pair<Int, Crucible>>()
        var left = turnLeft()
        var leftScore = score
        repeat(3) {
            left = left.forward()
            if (isValid(left.position, input)) {
                leftScore += input[left.position.first][left.position.second].toString().toInt()
            }
        }
        repeat(7) {
            left = left.forward()
            if (isValid(left.position, input)) {
                leftScore += input[left.position.first][left.position.second].toString().toInt()
                moves.add(leftScore to left)
            }
        }
        var right = turnRight()
        var rightScore = score
        repeat(3) {
            right = right.forward()
            if (isValid(right.position, input)) {
                rightScore += input[right.position.first][right.position.second].toString().toInt()
            }
        }
        repeat(7) {
            right = right.forward()
            if (isValid(right.position, input)) {
                rightScore += input[right.position.first][right.position.second].toString().toInt()
                moves.add(rightScore to right)
            }
        }
        return moves
    }

    fun nextMoves(score: Int, input: List<String>): Set<Pair<Int, Crucible>> {
        val moves = mutableSetOf<Pair<Int, Crucible>>()
        var left = turnLeft()
        var leftScore = score
        repeat(3) {
            left = left.forward()
            if (isValid(left.position, input)) {
                leftScore += input[left.position.first][left.position.second].toString().toInt()
                moves.add(leftScore to left)
            }
        }
        var right = turnRight()
        var rightScore = score
        repeat(3) {
            right = right.forward()
            if (isValid(right.position, input)) {
                rightScore += input[right.position.first][right.position.second].toString().toInt()
                moves.add(rightScore to right)
            }
        }
        return moves
    }

    private fun isValid(position: Pair<Int, Int>, input: List<String>): Boolean =
        position.first in input.indices && position.second in input[0].indices

    private fun forward(): Crucible {
        val nextPosition = when (direction) {
            NORTH -> position.first - 1 to position.second
            EAST -> position.first to position.second + 1
            SOUTH -> position.first + 1 to position.second
            WEST -> position.first to position.second - 1
        }
        return Crucible(nextPosition, direction)
    }

    private fun turnLeft(): Crucible = Crucible(
        position, when (direction) {
            NORTH -> WEST
            EAST -> NORTH
            SOUTH -> EAST
            WEST -> SOUTH
        }
    )

    private fun turnRight(): Crucible = Crucible(
        position, when (direction) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }
    )
}

fun minimumUltraHeatLoss(input: List<String>): Int = minimumHeatLoss(input, Crucible::nextUltraMoves)

fun minimumHeatLoss(
    input: List<String>,
    nextMoves: KFunction3<Crucible, Int, List<String>, Set<Pair<Int, Crucible>>> = Crucible::nextMoves
): Int {
    val visitedPositions = mutableSetOf<Crucible>()
    val toVisit = PriorityQueue<Pair<Int, Crucible>>(compareBy { it.first })
    toVisit.add(0 to Crucible(0 to 0, NORTH))
    toVisit.add(0 to Crucible(0 to 0, WEST))
    var best = Int.MAX_VALUE
    while (toVisit.isNotEmpty()) {
        val (score, crucible) = toVisit.poll()
        if (visitedPositions.contains(crucible) || score >= best) {
            continue
        }
        visitedPositions.add(crucible)
        if (crucible.position == input.lastIndex to input.last().lastIndex) {
            best = min(best, score)
        }
        toVisit.addAll(nextMoves.invoke(crucible, score, input))
    }
    return best
}
