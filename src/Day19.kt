import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("day19")
    sumPartRatingNumbers(input).println()
    countValidCombinations(input).println()
}

fun countValidCombinations(input: List<String>): Long {
    val (workflowLines, _) = input.joinToString("\n").split("\n\n").map { it.lines() }
    val workflows = parseWorkflows(workflowLines)
    return validParts("in", mapOf('x' to 1..4000, 'm' to 1..4000, 'a' to 1..4000, 's' to 1..4000), workflows)
}

fun validParts(label: String, ranges: Map<Char, IntRange>, workflows: Map<String, List<Condition>>): Long {
    if (label == "A") {
        return ranges.values.map { it.count().toLong() }.reduce(Long::times)
    }
    if (label == "R") {
        return 0L
    }
    val rules = workflows.getValue(label)
    var total = 0L
    val currentRanges = HashMap(ranges).toMutableMap()
    for (rule in rules) {
        total += when (rule) {
            is Goto -> validParts(rule.label, currentRanges, workflows)
            is Eval -> {
                val rangeToUpdate = currentRanges.getValue(rule.category)
                val recursionRanges = HashMap(currentRanges).toMutableMap()
                val (recursionRange, updatedRange) = when (rule.operator) {
                    '<' -> {
                        rangeToUpdate.first..min(rangeToUpdate.last, rule.value - 1) to max(
                            rangeToUpdate.first,
                            rule.value
                        )..rangeToUpdate.last
                    }

                    '>' -> {
                        max(rangeToUpdate.first, rule.value + 1)..rangeToUpdate.last to rangeToUpdate.first..min(
                            rangeToUpdate.last,
                            rule.value
                        )
                    }

                    else -> throw IllegalArgumentException("Unknown operator ${rule.operator}")
                }
                recursionRanges[rule.category] = recursionRange
                currentRanges[rule.category] = updatedRange
                validParts(rule.label, recursionRanges, workflows)
            }
        }
    }
    return total

}

typealias Part = Map<Char, Int>

sealed class Condition {
    abstract fun applyToPart(part: Map<Char, Int>): String?
}

data class Goto(val label: String) : Condition() {
    override fun applyToPart(part: Map<Char, Int>): String = label
}

data class Eval(val label: String, val category: Char, val operator: Char, val value: Int) : Condition() {
    override fun applyToPart(part: Map<Char, Int>): String? {
        return if (when (operator) {
                '>' -> part.getValue(category) > value
                '<' -> part.getValue(category) < value
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        ) {
            label
        } else {
            null
        }
    }

}

fun sumPartRatingNumbers(input: List<String>): Int {
    val (workflowLines, partLines) = input.joinToString("\n").split("\n\n").map { it.lines() }
    val workflows = parseWorkflows(workflowLines)
    val parts = parseParts(partLines)
    return parts.sumOf { ratingNumber(it, workflows) }
}

private fun parseParts(partLines: List<String>): List<Map<Char, Int>> {
    val parts = partLines.map { part ->
        val (x, m, a, s) = """\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)}""".toRegex().find(part)!!.destructured
            .toList().map(String::toInt)
        mapOf(
            'x' to x,
            'm' to m,
            'a' to a,
            's' to s
        )
    }
    return parts
}

private fun parseWorkflows(workflowLines: List<String>): Map<String, List<Condition>> {
    val workflows = workflowLines.associate { workflow ->
        val (name, rest) = workflow.dropLast(1).split("{")
        val rules = rest.split(",").map { rule ->
            if (rule.contains(":")) {
                val category = rule.first()
                val operator = rule[1]
                val (value, label) = rule.drop(2).split(":")
                Eval(label, category, operator, value.toInt())
            } else {
                Goto(rule)
            }
        }
        name to rules
    }
    return workflows
}

fun ratingNumber(part: Map<Char, Int>, workflows: Map<String, List<Condition>>): Int {
    return if (isValid(part, workflows)) {
        part.values.sum()
    } else {
        0
    }
}

fun isValid(part: Map<Char, Int>, workflows: Map<String, List<Condition>>): Boolean {
    var currentWorkflow = "in"
    while (currentWorkflow != "A" && currentWorkflow != "R") {
        var index = 0
        val conditions = workflows.getValue(currentWorkflow)
        var nextWorkflow: String? = null
        while (nextWorkflow == null) {
            nextWorkflow = conditions[index].applyToPart(part)
            index++
        }
        currentWorkflow = nextWorkflow
    }
    return currentWorkflow == "A"
}
