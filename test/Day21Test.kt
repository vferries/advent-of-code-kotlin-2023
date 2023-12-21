import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day21Test {
    @Test
    fun `Should could reachable garden plots in 6 steps`() {
        val sample = readInput("day21-sample")
        reachableGardenPlots(sample, 6) shouldBe 16
    }
}
