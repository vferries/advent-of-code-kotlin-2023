import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day19Test {
    @Test
    fun `Should compute parts rating numbers`() {
        val sample = readInput("day19-sample")
        sumPartRatingNumbers(sample) shouldBe 19114
    }
    @Test
    fun `Should compute valid combinations`() {
        val sample = readInput("day19-sample")
        countValidCombinations(sample) shouldBe 167409079868000
    }
}
