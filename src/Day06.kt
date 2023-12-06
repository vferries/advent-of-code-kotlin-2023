import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO
import kotlin.system.measureTimeMillis

fun main() {
    val input = readInput("day06")
    part1(input).println()
    println("total for part to " + measureTimeMillis {
        part2(input).println()
    } + "ms")
}


private fun part1(input: List<String>): Int {
    val races = parseRaces(input)
    return races.map(::margins).fold(1, Int::times)
}

private fun part2(input: List<String>): BigInteger {
    val race = parseRace(input)
    return margins(race)
}

fun parseRaces(input: List<String>): List<Pair<Int, Int>> {
    val times = input[0].split(Regex("Time:\\s+"))[1].split(Regex("\\s+")).map(String::toInt)
    val distances = input[1].split(Regex("Distance:\\s+"))[1].split(Regex("\\s+")).map(String::toInt)
    return times.zip(distances)
}

fun parseRace(input: List<String>): Pair<BigInteger, BigInteger> {
    val time = input[0].split(Regex("Time:\\s+"))[1].split(Regex("\\s+")).joinToString("").toBigInteger()
    val distance = input[1].split(Regex("Distance:\\s+"))[1].split(Regex("\\s+")).joinToString("").toBigInteger()
    return time to distance
}

fun margins(race: Pair<Int, Int>): Int {
    val (time, best) = race
    return (0..time).count { timePressed ->
        timePressed * (time - timePressed) > best
    }
}

fun margins(race: Pair<BigInteger, BigInteger>): BigInteger {
    val (time, best) = race
    var timePressed = ONE
    var total = ZERO
    while (timePressed <= time) {
        if (timePressed * (time - timePressed) > best) {
            total += ONE
        }
        timePressed += ONE
    }
    return total
}
