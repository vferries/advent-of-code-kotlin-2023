import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day18Test {
    @Test
    fun `Should compute lava pool area`() {
        val sample = readInput("day18-sample")
        lavaPoolArea(sample) shouldBe 62
    }
    @Test
    fun `Should compute corrected lava pool area`() {
        val sample = readInput("day18-sample")
        correctedLavaPoolArea(sample) shouldBe BigInteger("952408144115")
    }
}
