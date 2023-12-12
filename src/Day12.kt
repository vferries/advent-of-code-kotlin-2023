fun main() {
    val input = readInput("day12")
    possibleArrangements(input).println()
    possibleArrangementsPart2(input).println()
}

fun possibleArrangementsPart2(input: List<String>): Long {
    var lineNumber = 0
    return input.sumOf { line ->
        val (springStatus, contiguousGroups) = line.split(" ")
        var groups = contiguousGroups.split(",").map(String::toInt)
        val status = Array(5) { springStatus }.joinToString("?", prefix = ".", postfix = ".")
        groups = Array(5) { groups }.flatMap { it }
        validArrangements(status, groups, hashMapOf())
    }
}

fun possibleArrangements(input: List<String>): Long = input.sumOf { possibleArrangements(it) }

fun possibleArrangements(line: String): Long {
    val (springStatus, contiguousGroups) = line.split(" ")
    val groups = contiguousGroups.split(",").map(String::toInt)
    return validArrangements(".$springStatus.", groups, hashMapOf())
}

fun validArrangements(str: String, groups: List<Int>, memo: HashMap<Pair<String, List<Int>>, Long>): Long {
    if (groups.isEmpty()) {
        return if (str.contains("#")) {
            0
        } else {
            1
        }
    } else {
        if (memo.containsKey(str to groups)) {
            return memo.getValue(str to groups)
        } else {
            val first = groups.first()
            val rest = groups.drop(1)
            val pattern = "(?=([.?][#?]{$first}[.?]))"
            val matches = Regex(pattern).findAll(str)
            var total = 0L
            for (match in matches) {
                val firstHash = str.indexOf("#")
                if (firstHash == -1 || firstHash >= match.groups[1]!!.range.first) {
                    total += validArrangements(
                        "." + str.substring(match.groups[1]!!.range.last + 1),
                        rest,
                        memo
                    )
                }
            }
            memo[str to groups] = total
            return total
        }
    }
}
