fun main() {
    val input = readInput("day02")
    sumPossible(input).println()
    sumPowers(input).println()
}

fun sumPossible(lines: List<String>): Int = lines
    .map { line -> parseLine(line) }
    .filter { game -> isPossible(game) }
    .sumOf { it.id }

fun sumPowers(lines: List<String>): Int = lines
    .map { line -> parseLine(line) }
    .sumOf { it.power }

fun isPossible(game: Game): Boolean =
    game.rounds.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 }

fun parseLine(line: String): Game {
    val (left, right) = line.split(": ")
    val id = left.filter(Char::isDigit).toInt()
    val draws = right.split("; ")
    return Game(id, draws.map { it.toColor() })
}

private fun String.toColor(): Color =
    Color(findColor("red"), findColor("green"), findColor("blue"))

private fun String.findColor(color: String) = "(\\d+) $color".toRegex().find(this)?.groupValues?.get(1)?.toInt() ?: 0
