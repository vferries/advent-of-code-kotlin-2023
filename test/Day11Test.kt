import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day11Test {
    @Test
    fun `Should find length of shortest paths between galaxies in expanded universe sample`() {
        val sample = readInput("day11-sample")
        lengthOfShortestPaths(sample) shouldBe 374
    }

}
