fun main() {
    val input = readInput("day01")
    sumCalibrations(input, String::calibrationValue).println()
    sumCalibrations(input, String::calibrationValuePart2).println()
}

fun sumCalibrations(input: List<String>, method: (String) -> Int): Int = input.sumOf(method)
fun String.calibrationValue(): Int {
    val digits = this.filter(Char::isDigit)
    return digits.first().digitToInt() * 10 + digits.last().digitToInt()
}

val spelledDigits = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun String.calibrationValuePart2(): Int {
    val possibleDigits = spelledDigits.keys + spelledDigits.values
    val firstIndex = this.indexOfAny(possibleDigits)
    val lastIndex = this.lastIndexOfAny(possibleDigits)
    return indexToDigit(this, firstIndex) * 10 + indexToDigit(this, lastIndex)
}

fun indexToDigit(string: String, index: Int): Int {
    val digit = if (string[index].isDigit()) {
        string[index]
    } else {
        spelledDigits.entries.find {
            it.key.startsWith(string.substring(index..index + 2))
        }!!.value.first()
    }
    return digit.digitToInt()
}
