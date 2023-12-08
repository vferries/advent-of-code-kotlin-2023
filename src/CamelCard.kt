enum class CamelCard {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, T, J, Q, K, A;

    companion object {
        fun fromChar(c: Char): CamelCard = when (c) {
            '2' -> TWO
            '3' -> THREE
            '4' -> FOUR
            '5' -> FIVE
            '6' -> SIX
            '7' -> SEVEN
            '8' -> EIGHT
            '9' -> NINE
            'T' -> T
            'J' -> J
            'Q' -> Q
            'K' -> K
            'A' -> A
            else -> throw IllegalArgumentException("Unknown card $c")
        }
    }
}