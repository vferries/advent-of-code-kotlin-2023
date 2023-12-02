import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File

class Day02Test {
    @Test
    fun `Should correctly parse a line`() {
        val line = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        parseLine(line) shouldBe Game(
            1, listOf(
                Color(4, 0, 3),
                Color(1, 2, 6),
                Color(0, 2, 0)
            )
        )
    }

    @Test
    fun `Should sum ids of possible games on sample`() {
        val sampleLines = readInput("day02-sample")
        val result = sumPossible(sampleLines)
        result shouldBe 8
    }

    @ParameterizedTest(name = "Power for game {0} shouldBe {1}")
    @CsvSource(value = ["1:48", "2:12", "3:1560", "4:630", "5:36"], delimiter = ':')
    fun `Should correctly calculate power`(gameId: Int, expectedPower: Int) {
        val sampleLines = readInput("day02-sample")
        parseLine(sampleLines[gameId - 1]).power shouldBe expectedPower
    }

    @Test
    fun `Should sum game powers for sample`() {
        val sampleLines = readInput("day02-sample")
        val result = sumPowers(sampleLines)
        result shouldBe 2286
    }

}
