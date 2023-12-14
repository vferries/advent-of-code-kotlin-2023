import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day13Test {
    @Test
    fun `Should find horizontal mirror position`() {
        val sample = readInput("day13-sample")
        val maps = parseMirrors(sample)
        findHorizontalSymmetry(maps[1]) shouldBe listOf(4)
    }

    @Test
    fun `Should find vertical mirror position`() {
        val sample = readInput("day13-sample")
        val maps = parseMirrors(sample)
        findVerticalSymmetry(maps[0]) shouldBe listOf(5)
    }

    @Test
    fun `Should compute correct score for sample`() {
        val sample = readInput("day13-sample")
        val maps = parseMirrors(sample)
        summarizeNotes(maps) shouldBe 405
    }
    @Test
    fun `Should find horizontal mirror position at the end`() {
        val map = listOf(
            "...###",
            "#.##.#",
            "#.##.#"
        )
        findHorizontalSymmetry(map) shouldBe listOf(2)
    }
    @Test
    fun `Should compute correct score with smudge for sample`() {
        val sample = readInput("day13-sample")
        val maps = parseMirrors(sample)
        summarizeWithSmudge(maps) shouldBe 400
    }
}
