import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day10Test {
    @Test
    fun `Should compute further distance in the loop for sample 1`() {
        val sample1 = readInput("day10-sample1")
        computeFurtherDistance(sample1) shouldBe 4
    }

    @Test
    fun `Should compute further distance in the loop for sample 2`() {
        val sample2 = readInput("day10-sample2")
        computeFurtherDistance(sample2) shouldBe 8
    }

    @Test
    fun `Should compute enclosed tiles for sample 3`() {
        val sample2 = readInput("day10-sample3")
        countEnclosedTiles(sample2) shouldBe 4
    }

    @Test
    fun `Should compute enclosed tiles for sample 4`() {
        val sample2 = readInput("day10-sample4")
        countEnclosedTiles(sample2) shouldBe 8
    }

    @Test
    fun `Should compute enclosed tiles for sample 5`() {
        val sample2 = readInput("day10-sample5")
        countEnclosedTiles(sample2) shouldBe 10
    }
}
