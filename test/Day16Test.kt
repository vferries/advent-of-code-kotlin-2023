import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day16Test {
    @Test
    fun `Should count energized tiles`() {
        val sample = readInput("day16-sample")
        countTilesEnergized(sample) shouldBe 46
    }
    @Test
    fun `Should find max energized tiles`() {
        val sample = readInput("day16-sample")
        maxTilesEnergized(sample) shouldBe 51
    }
}
