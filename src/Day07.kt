import CamelCard.J

fun main() {
    val input = readInput("day07")
    day07part1(input).println()
    day07part2(input).println()
}

typealias Hand = List<CamelCard>

fun parseHandAndBid(line: String): Pair<Hand, Int> {
    val (hand, bid) = line.split(" ")
    return hand.map { c -> CamelCard.fromChar(c) } to bid.toInt()
}

val Hand.type: HandType
    get() {
        val counts = this.groupBy { it }.mapValues { it.value.size }
        return when {
            counts.values.contains(5) -> HandType.FIVE_OF_A_KIND
            counts.values.contains(4) -> HandType.FOUR_OF_A_KIND
            counts.values.contains(3) && counts.values.contains(2) -> HandType.FULL_HOUSE
            counts.values.contains(3) -> HandType.THREE_OF_A_KIND
            counts.values.filter { it == 2 }.size == 2 -> HandType.TWO_PAIRS
            counts.values.contains(2) -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

val Hand.typeWithJokers: HandType
    get() {
        val counts = this.groupBy { it }.mapValues { it.value.size }.toMutableMap()
        val jokers = counts.getOrElse(J) { 0 }
        counts.remove(J)
        val max = counts.values.maxOrNull() ?: 0
        return when {
            max + jokers == 5 -> HandType.FIVE_OF_A_KIND
            max + jokers == 4 -> HandType.FOUR_OF_A_KIND
            (counts.values.contains(3) && counts.values.contains(2)) || (counts.values.filter { it == 2 }.size == 2 && jokers == 1) -> HandType.FULL_HOUSE
            max + jokers == 3 -> HandType.THREE_OF_A_KIND
            counts.values.filter { it == 2 }.size == 2 -> HandType.TWO_PAIRS
            max + jokers == 2 -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

fun day07part1(input: List<String>): Int {
    val handsWithBid = input.map(::parseHandAndBid)
    val hands = handsWithBid
        .sortedWith(
            compareBy<Pair<Hand, Int>> { it.first.type }.thenComparing(HighestInOrderComparator())
        )
    return hands.foldIndexed(0) { index, acc, (_, bid) ->
        acc + (index + 1) * bid
    }
}

fun day07part2(input: List<String>): Int {
    val handsWithBid = input.map(::parseHandAndBid)
    val hands = handsWithBid
        .sortedWith(
            compareBy<Pair<Hand, Int>> { it.first.typeWithJokers }.thenComparing(HighestInOrderComparatorPart2())
        )
    println(hands)
    return hands.foldIndexed(0) { index, acc, (_, bid) ->
        acc + (index + 1) * bid
    }
}
