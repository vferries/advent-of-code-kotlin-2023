import kotlin.math.pow

class Card(val winning: List<Int>, val possessed: List<Int>) {
    val matchings: Int
        get() = possessed.filter { winning.contains(it) }.size
    val score: Int
        get() = 2f.pow(matchings).toInt() / 2
}
