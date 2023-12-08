import CamelCard.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {
    @Test
    fun `Should parse Camel Hand`() {
        parseHandAndBid("32T3K 765") shouldBe (listOf(
            THREE, TWO, T, THREE, K
        ) to 765)
    }

    @Test
    fun `Should compute type for five of a kind`() {
        parseHandAndBid("55555 765").first.type shouldBe HandType.FIVE_OF_A_KIND
    }

    @Test
    fun `Should compute type for four of a kind`() {
        parseHandAndBid("4444A 765").first.type shouldBe HandType.FOUR_OF_A_KIND
    }

    @Test
    fun `Should compute type for full house`() {
        parseHandAndBid("333QQ 765").first.type shouldBe HandType.FULL_HOUSE
    }

    @Test
    fun `Should compute type for three of a kind`() {
        parseHandAndBid("777A2 765").first.type shouldBe HandType.THREE_OF_A_KIND
    }

    @Test
    fun `Should compute type for two pairs`() {
        parseHandAndBid("AJA5J 765").first.type shouldBe HandType.TWO_PAIRS
    }

    @Test
    fun `Should compute type for one pair`() {
        parseHandAndBid("A4342 765").first.type shouldBe HandType.ONE_PAIR
    }

    @Test
    fun `Should compute type for high card`() {
        parseHandAndBid("A2345 765").first.type shouldBe HandType.HIGH_CARD
    }

    @Test
    fun `Should compute total winnings for sample`() {
        val input = readInput("day07-sample")
        day07part1(input) shouldBe 6440
    }

    @Test
    fun `Should compute total winnings part 2 for sample`() {
        val input = readInput("day07-sample")
        day07part2(input) shouldBe 5905
    }
}
