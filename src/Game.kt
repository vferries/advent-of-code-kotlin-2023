data class Game(val id: Int, val rounds: List<Color>) {
    val power: Int
        get() =
            rounds.maxOf { it.red } * rounds.maxOf { it.green } * rounds.maxOf { it.blue }
}
