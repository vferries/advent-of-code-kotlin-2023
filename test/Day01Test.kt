import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {
    @Test
    fun `Should return correct calibration value for first sample line`() {
        "1abc2".calibrationValue() shouldBe 12
    }

    @Test
    fun `Should return correct calibration value for second sample line`() {
        "pqr3stu8vwx".calibrationValue() shouldBe 38
    }

    @Test
    fun `Should return correct calibration value for third sample line`() {
        "a1b2c3d4e5f".calibrationValue() shouldBe 15
    }

    @Test
    fun `Should return correct calibration value for forth sample line`() {
        "treb7uchet".calibrationValue() shouldBe 77
    }

    @Test
    fun `Should return correct sum of calibration for part 1 sample`() {
        val sample = """1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet""".trimIndent().lines()
        sumCalibrations(sample, String::calibrationValue) shouldBe 142
    }

    @Test
    fun `Should return correct sum of calibration for part 2 sample`() {
        val sample = """two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen""".trimIndent().lines()
        sumCalibrations(sample, String::calibrationValuePart2) shouldBe 281
    }
}
