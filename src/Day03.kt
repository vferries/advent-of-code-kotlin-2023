import java.util.*

fun main() {
    val input = readInput("day03")
    sumPartNumbers(input).println()
    sumGearRatios(input).println()
}

fun sumPartNumbers(input: List<String>): Int {
    var total = 0
    val extended = input.map { "$it." }
    for ((row, line) in extended.withIndex()) {
        var startIndex: OptionalInt = OptionalInt.empty()
        for ((col, c) in line.withIndex()) {
            if (c.isDigit()) {
                //on initialise startIndex si vide
                if (startIndex.isEmpty) {
                    startIndex = OptionalInt.of(col)
                }
                //sinon on continue
            } else {
                if (startIndex.isPresent) {
                    val start = startIndex.asInt
                    val number = line.substring(start..<col).toInt()
                    println(number)
                    //on vérifie s'il est proche d'un symbole
                    var shouldBeAdded = false
                    for (i in start - 1..col) {
                        val topRow = input.getOrElse(row - 1) { "" }.getOrElse(i) { '.' }
                        if (!topRow.isDigit() && topRow != '.') {
                            shouldBeAdded = true
                        }
                        print(topRow)
                    }
                    println()
                    val left = input[row].getOrElse(start - 1) { '.' }
                    print(left)
                    if (!left.isDigit() && left != '.') {
                        shouldBeAdded = true
                    }
                    val right = input[row].getOrElse(col) { '.' }
                    println(right)
                    if (!right.isDigit() && right != '.') {
                        shouldBeAdded = true
                    }
                    for (i in start - 1..col) {
                        val bottomRow = input.getOrElse(row + 1) { "" }.getOrElse(i) { '.' }
                        if (!bottomRow.isDigit() && bottomRow != '.') {
                            shouldBeAdded = true
                        }
                        print(bottomRow)
                    }
                    println()

                    if (shouldBeAdded) {
                        println("valid")
                        total += number
                    }
                    startIndex = OptionalInt.empty()
                }
            }
        }
    }
    return total
}

fun sumGearRatios(input: List<String>): Int {
    val starsWithNumbers = mutableMapOf<Pair<Int, Int>, List<Int>>().withDefault { listOf() }
    val extended = input.map { "$it." }
    for ((row, line) in extended.withIndex()) {
        var startIndex: OptionalInt = OptionalInt.empty()
        for ((col, c) in line.withIndex()) {
            if (c.isDigit()) {
                //on initialise startIndex si vide
                if (startIndex.isEmpty) {
                    startIndex = OptionalInt.of(col)
                }
                //sinon on continue
            } else {
                if (startIndex.isPresent) {
                    val start = startIndex.asInt
                    val number = line.substring(start..<col).toInt()
                    //on vérifie s'il est proche d'une étoile
                    for (i in start - 1..col) {
                        val topRow = input.getOrElse(row - 1) { "" }.getOrElse(i) { '.' }
                        if (topRow == '*') {
                            starsWithNumbers[row - 1 to i] = starsWithNumbers.getValue(row - 1 to i) + number
                        }
                    }
                    val left = input[row].getOrElse(start - 1) { '.' }
                    if (left == '*') {
                        starsWithNumbers[row to start - 1] = starsWithNumbers.getValue(row to start - 1) + number
                    }
                    val right = input[row].getOrElse(col) { '.' }
                    if (right == '*') {
                        starsWithNumbers[row to col] = starsWithNumbers.getValue(row to col) + number
                    }
                    for (i in start - 1..col) {
                        val bottomRow = input.getOrElse(row + 1) { "" }.getOrElse(i) { '.' }
                        if (bottomRow == '*') {
                            starsWithNumbers[row + 1 to i] = starsWithNumbers.getValue(row + 1 to i) + number
                        }
                    }
                    startIndex = OptionalInt.empty()
                }
            }
        }
    }
    println(starsWithNumbers)
    return starsWithNumbers.filter { it.value.size == 2 }
        .map { it.value[0] * it.value[1] }
        .sum()
}

/*
fun sumGearRatios(input: List<String>): Int {
    var total = 0
    val extended = input.map { "$it." }
    for ((row, line) in extended.withIndex()) {
        for ((col, c) in line.withIndex()) {
            if (c != '*') {
                break
            }
            var adjacentNumbers = 0
            val top = "" + input.getOrElse(row - 1) { "" }.getOrElse(col - 1) { '.' } +
                    input.getOrElse(row - 1) { "" }.getOrElse(col) { '.' } +
                    input.getOrElse(row - 1) { "" }.getOrElse(col + 1) { '.' }
            val topDigits = top.filter { it.isDigit() }.length
            if (topDigits == 2 && !top[1].isDigit()) {
                adjacentNumbers += 2
            } else {

            }
            val left = input.getOrElse(row) { "" }.getOrElse(col - 1) { '.' }
            if (left.isDigit()) {
                adjacentNumbers += 1
            }
            val right = input.getOrElse(row) { "" }.getOrElse(col + 1) { '.' }
            if (right.isDigit()) {
                adjacentNumbers += 1
            }
            val bottom = "" + input.getOrElse(row + 1) { "" }.getOrElse(col - 1) { '.' } +
                    input.getOrElse(row + 1) { "" }.getOrElse(col) { '.' } +
                    input.getOrElse(row + 1) { "" }.getOrElse(col + 1) { '.' }
        }
    }
    return total
}
 */
