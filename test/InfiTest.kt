import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class InfiTest {
    @Test
    fun `Should correctly compute minimum radius for sample package`() {
        Package.fromString("(0, 4), (3, -2), (-1, -2), (-2, 0)").minRadiusFromZero shouldBe 4
    }

    @Test
    fun `Should correctly compute minimum radius for second sample part 2`() {
        Package.fromString("(-1, 0), (1, 4), (1, -4)").minRadius shouldBe 4
    }

    @Test
    fun `Should correctly compute minimum radius for first sample part2`() {
        Package.fromString("(0, 4), (3, -2), (-1, -2), (-2, 0)").minRadius shouldBe 3.400367
    }
}