import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {
    @Test
    fun `Should return correct sum of part numbers for sample`() {
        sumPartNumbers(readInput("day03-sample")) shouldBe 4361
    }

    @Test
    fun `Should return correct gear ratio for sample`() {
        sumGearRatios(readInput("day03-sample")) shouldBe 467835
    }
}
