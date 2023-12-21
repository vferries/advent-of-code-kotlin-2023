import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day20Test {
    @Test
    fun `Should count signals for sample 1`() {
        val sample = readInput("day20-sample1")
        countSignalsWhenPushingButton(sample, 1000) shouldBe 32000000
    }


    @Test
    fun `Should count signals for sample 2`() {
        val sample = readInput("day20-sample2")
        countSignalsWhenPushingButton(sample, 1000) shouldBe 11687500
    }
}
