fun main() {
    val input = readInput("day08")
    desertPart1(input).println()
    desertPart2(input).println()
}

fun desertPart1(lines: List<String>): Int {
    val (instructions, network) = parseDesertMaps(lines)
    var currentPosition = "AAA"
    var currentStep = 0
    while (currentPosition != "ZZZ") {
        val choices = network.getValue(currentPosition)
        currentPosition = when (instructions[currentStep % instructions.length]) {
            'L' -> choices.first
            'R' -> choices.second
            else -> throw IllegalArgumentException("Unsupported instruction ${instructions[currentStep % instructions.length]}")
        }
        currentStep++
    }
    return currentStep
}

fun desertPart2(lines: List<String>): Long {
    val (instructions, network) = parseDesertMaps(lines)
    val currentPositions = network.keys.filter { it.endsWith("A") }

    val loopSizes = currentPositions.map {
        var currentStep = 0
        var currentPosition = it
        val visited = mutableListOf<Pair<Int, String>>()
        while (!visited.contains(currentStep % instructions.length to currentPosition)) {
            visited.add(currentStep % instructions.length to currentPosition)
            val choices = network.getValue(currentPosition)
            currentPosition = when (instructions[currentStep % instructions.length]) {
                'L' -> choices.first
                'R' -> choices.second
                else -> throw IllegalArgumentException("Unsupported instruction ${instructions[currentStep % instructions.length]}")
            }
            currentStep++
        }
        val loopItem = currentStep % instructions.length to currentPosition
        val loopSize = visited.size - visited.indexOf(loopItem)
        loopSize.toLong()
    }
    return loopSizes.reduce(::lcm)
}

fun lcm(a: Long, b: Long) = a * b / gcd(a, b)

fun gcd(a: Long, b: Long): Long = if (b == 0L) {
    a
} else {
    gcd(b, a % b)
}

private fun parseDesertMaps(lines: List<String>): Pair<String, Map<String, Pair<String, String>>> {
    val network = lines.drop(2).associate { line ->
        val (key, left, right) = Regex("(\\S+) = \\((\\S+), (\\S+)\\)").find(line)!!.destructured
        key to (left to right)
    }
    return lines[0] to network
}