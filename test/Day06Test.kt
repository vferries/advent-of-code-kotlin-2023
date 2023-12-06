import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {
    @Test
    fun `Should compute multiplication of margins for sample`() {
        val input = readInput("day06-sample")
        part1(input) shouldBe 288
    }
}
