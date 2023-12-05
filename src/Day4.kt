fun main() {
    val input = readInput("day04")
    input.sumOf { parseCard(it).score }.println()
    computeCardsPossessed(input).println()
}

fun parseCard(card: String): Card {
    val numbers = card.split(":\\s+".toRegex())[1]
    val (winningNumbers, numbersYouHave) = numbers.split("\\s+\\|\\s+".toRegex())
    return Card(
        winningNumbers.split("\\s+".toRegex()).map { it.toInt() },
        numbersYouHave.split("\\s+".toRegex()).map { it.toInt() }
    )
}

fun computeCardsPossessed(initialCards: List<String>): Int {
    val cards = initialCards.map(::parseCard)

    val cardsCount = IntArray(initialCards.size) { 1 }

    for ((index, card) in cards.withIndex()) {
        for (i in 1..card.matchings) {
            if (index + i < cardsCount.size)
            cardsCount[index + i] += cardsCount[index]
        }
    }

    return cardsCount.sum()
}
