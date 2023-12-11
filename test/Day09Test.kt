import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day09Test {
    @Test
    fun `Should calculate extrapolated values correctly`() {
        val sample = readInput("day09-sample")
        sumOfExtrapolatedValues(sample) shouldBe 114
    }

    @Test
    fun `Should calculate backwards extrapolated values correctly`() {
        val sample = readInput("day09-sample")
        sumOfBackwardsExtrapolatedValues(sample) shouldBe 2
    }
}