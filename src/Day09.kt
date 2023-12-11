fun main() {
    val input = readInput("day09")
    sumOfExtrapolatedValues(input).println()
    sumOfBackwardsExtrapolatedValues(input).println()
}

fun sumOfExtrapolatedValues(input: List<String>): Int {
    return input.sumOf { calculateExtrapolatedValue(it) }
}

fun calculateExtrapolatedValue(line: String): Int {
    val numbers = extrapolateValues(line)
    return numbers.sumOf(List<Int>::last)
}

fun sumOfBackwardsExtrapolatedValues(input: List<String>): Int {
    return input.sumOf { calculateBackwardsExtrapolatedValue(it) }
}

fun calculateBackwardsExtrapolatedValue(line: String): Int {
    val numbers = extrapolateValues(line)
    var previous = 0
    for (extrapolation in numbers.reversed()) {
        previous = extrapolation.first() - previous
    }
    return previous
}

private fun extrapolateValues(line: String): MutableList<List<Int>> {
    val numbers = mutableListOf(line.split(" ").map(String::toInt))
    while (!numbers.last().all { it == 0 }) {
        val newList = numbers.last().windowed(2).map { l -> l[1] - l[0] }
        numbers.add(newList)
    }
    return numbers
}
