import kotlin.math.floor

fun main() {
    val input = readInput("infi")
    println(floor(input.sumOf { line -> Package.fromString(line).minRadiusFromZero }).toInt())
    println(floor(input.sumOf { line -> Package.fromString(line).minRadius }).toInt())
}