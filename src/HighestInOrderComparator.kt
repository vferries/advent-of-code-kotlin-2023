import CamelCard.J

class HighestInOrderComparator: Comparator<Pair<Hand, Int>> {
    override fun compare(h1: Pair<Hand, Int>, h2: Pair<Hand, Int>): Int {
        val hand1 = h1.first
        val hand2 = h2.first
        for ((c1, c2) in hand1.zip(hand2)) {
            if (c1 != c2) {
                return c1.compareTo(c2)
            }
        }
        return 0
    }
}