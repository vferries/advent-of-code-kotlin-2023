import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day17Test {
    @Test
    fun `Should compute minimum heat loss`() {
        val sample = readInput("day17-sample")
        minimumHeatLoss(sample) shouldBe 102
    }

    @Test
    fun `Should compute minimum ultra heat loss`() {
        val sample = readInput("day17-sample")
        minimumUltraHeatLoss(sample) shouldBe 94
    }
}
