import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day14Test {
    @Test
    fun `Should compute total load`() {
        val sample = readInput("day14-sample")
        computeTotalLoad(sample) shouldBe 136
    }

    @Test
    fun `Should compute full cycle`() {
        val sample = readInput("day14-sample")
        val expected = """.....#....
....#...O#
...OO##...
.OO#......
.....OOO#.
.O#...O#.#
....O#....
......OOOO
#...O###..
#..OO#...."""
        fullCycle(sample) shouldBe expected.lines()
    }


    @Test
    fun `Should compute total final load`() {
        val sample = readInput("day14-sample")
        totalFinalLoad(sample) shouldBe 64
    }

}
