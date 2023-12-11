import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {
    @Test
    fun `Should compute correct number of steps for sample1`() {
        val input = readInput("day08-sample1")
        desertPart1(input) shouldBe 2
    }
    @Test
    fun `Should compute correct number of steps for sample2`() {
        val input = readInput("day08-sample2")
        desertPart1(input) shouldBe 6
    }
    @Test
    fun `Should compute correct number of steps for sample3`() {
        val input = readInput("day08-sample3")
        desertPart2(input) shouldBe 6
    }
}
