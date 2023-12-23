import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day22Test {
    @Test
    fun `Should count bricks that can be disintegrated`() {
        val sample = readInput("day22-sample")
        countDisintegratableBricks(sample) shouldBe 5
    }

    @Test
    fun `Should find best brick to desintegrate`() {
        val sample = readInput("day22-sample")
        findBestBrickToDisintegrate(sample) shouldBe 7
    }
}
