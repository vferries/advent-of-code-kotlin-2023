import kotlin.math.min

fun main() {
    val input = readInput("day05")
    val (seeds, maps) = parseAlmanach(input)
    seeds.minOfOrNull { seedToLocation(it, maps) }.println()
    val pairs = seeds.chunked(2)
    pairs.minOfOrNull { bestLocation(it[0]..<it[0] + it[1], maps) }.println()
}

fun bestLocation(range: LongRange, maps: Map<Pair<String, String>, List<Mapping>>): Long {
    var currentRanges = listOf(range)
    var currentType = "seed"
    while (maps.keys.find { it.first == currentType } != null) {
        val entry = maps.entries.find { it.key.first == currentType }!!
        currentType = entry.key.second
        val mappings = entry.value

        currentRanges = currentRanges.flatMap { range ->
            var i = range.first
            val ranges = mutableListOf<LongRange>()
            while (i <= range.last) {
                if (mappings.find { it.source <= i && it.source + it.rangeLength > i } != null) {
                    // on ajoute le range max à partir de ce mapping, on augmente i
                    val mapping = mappings.find { i >= it.source && i < it.source + it.rangeLength }!!

                    val nbElems = min(range.last - i + 1, mapping.source - i + mapping.rangeLength)
                    ranges += ((i + mapping.destination - mapping.source)..<(i + mapping.destination - mapping.source)+nbElems)
                    i += nbElems
                } else {
                    // on ajoute un range jusqu'au prochain mapping, on augmente i à cette valeur
                    val next = mappings.map(Mapping::source).filter { it > i }.minOrNull() ?: Long.MAX_VALUE
                    val last = min(next - 1, range.last)
                    ranges += (i..last)
                    i = last + 1
                }
            }
            ranges
        }
    }
    return currentRanges.minOf { it.first }
}

fun seedToLocation(seed: Long, maps: Map<Pair<String, String>, List<Mapping>>): Long {
    var currentType = "seed"
    var currentValue = seed
    while (maps.keys.find { it.first == currentType } != null) {
        val entry = maps.entries.find { it.key.first == currentType }!!
        currentType = entry.key.second
        val mappings = entry.value
        mappings.find { it.source <= currentValue && it.source + it.rangeLength > currentValue }?.let {
            currentValue += it.destination - it.source
        }
    }
    return currentValue
}

fun parseAlmanach(almanach: List<String>): Pair<List<Long>, Map<Pair<String, String>, List<Mapping>>> {
    val seeds = almanach[0].split(": ")[1].split(" ").map(String::toLong)
    val maps = almanach.drop(2).joinToString("\n").split(Regex("\n\n")).map(String::lines).associate(::parseMap)
    return seeds to maps
}

fun parseMap(map: List<String>): Pair<Pair<String, String>, List<Mapping>> {
    val (from, to) = """^(.+)-to-(.+) map:$""".toRegex().find(map[0])!!.destructured
    return (from to to) to map.drop(1).map(::parseMappings)
}

fun parseMappings(line: String): Mapping {
    val (destination, source, range) = line.split(" ").map(String::toLong)
    return Mapping(source, destination, range)
}
