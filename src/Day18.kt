import java.math.BigInteger
import java.math.BigInteger.*

fun main() {
    val input = readInput("day18")
    lavaPoolArea(input).println()
    correctedLavaPoolArea(input).println()
}

fun correctedLavaPoolArea(input: List<String>): BigInteger {
    var position = ZERO to ZERO
    val positions = mutableListOf(position)
    var perimeter = ZERO
    for (instruction in input) {
        val (_, _, color) = instruction.split(" ")
        val digits = color.drop(2).dropLast(1)
        val steps = digits.take(5).toBigInteger(16)
        val delta = when (val direction = digits.last().toString().toInt()) {
            3 -> -steps to ZERO
            1 -> steps to ZERO
            2 -> ZERO to -steps
            0 -> ZERO to steps
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
        position = position.add(delta)
        positions.add(position)
        perimeter += steps
    }
    // Shoelace formula
    return (positions.windowed(2)
        .sumOf { pair -> pair[0].second * pair[1].first - pair[0].first * pair[1].second } + perimeter) / TWO + ONE
}

fun lavaPoolArea(input: List<String>): Int {
    var position = 0 to 0
    val positions = mutableListOf(position)
    var perimeter = 0
    for (instruction in input) {
        val (direction, stepsStr, _) = instruction.split(" ")
        val steps = stepsStr.toInt()
        val delta = when (direction) {
            "U" -> -steps to 0
            "D" -> steps to 0
            "L" -> 0 to -steps
            "R" -> 0 to steps
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
        position += delta
        positions.add(position)
        perimeter += steps
    }
    // Shoelace formula
    return (positions.windowed(2)
        .sumOf { pair -> pair[0].second * pair[1].first - pair[0].first * pair[1].second } + perimeter) / 2 + 1
}

private operator fun Position.plus(delta: Position): Position = this.first + delta.first to this.second + delta.second

private fun Pair<BigInteger, BigInteger>.add(delta: Pair<BigInteger, BigInteger>): Pair<BigInteger, BigInteger> = this.first + delta.first to this.second + delta.second
