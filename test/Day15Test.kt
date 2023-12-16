import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day15Test {
    @Test
    fun `Should hash the HASH`() {
        hash("HASH") shouldBe 52
    }

    @Test
    fun `Should compute initialization sequence hash`() {
        val sample = readInput("day15-sample")
        initializationSequence(sample) shouldBe 1320
    }

    @Test
    fun `Should compute focusing power`() {
        val sample = readInput("day15-sample")
        focusingPower(sample) shouldBe 145
    }
}