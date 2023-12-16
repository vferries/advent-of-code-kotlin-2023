fun main() {
    val input = readInput("day15")
    initializationSequence(input).println()
    focusingPower(input).println()
}

fun focusingPower(input: List<String>): Int {
    val boxes = mutableMapOf<Int, MutableList<Lens>>().withDefault { mutableListOf() }
    val instructions = input[0].split(",")
    for (instruction in instructions) {
        when {
            instruction.endsWith('-') -> {
                val label = instruction.dropLast(1)
                val box = hash(label)
                val lenses = boxes.getValue(box)
                lenses.removeIf { it.label == label }
            }

            instruction.contains("=") -> {
                val (label, focal) = instruction.split("=")
                val lens = Lens(label, focal.toInt())
                val box = hash(label)
                val lenses = boxes.getValue(box)
                val index = lenses.indexOfFirst { it.label == label }
                if (index != -1) {
                    lenses[index] = lens
                } else {
                    lenses.add(lens)
                    boxes[box] = lenses
                }
            }
        }
    }
    return boxes.map { entry ->
        val (box, lenses) = entry
        (box + 1) * lenses.mapIndexed { i, lens -> (i + 1) * lens.focalLength }.sum()
    }.sum()
}

fun initializationSequence(input: List<String>): Int = input[0].split(",").sumOf(::hash)

fun hash(input: String): Int = input.fold(0) { acc, c ->
    ((acc + c.code) * 17) % 256
}
