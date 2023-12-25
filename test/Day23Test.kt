import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max

class Day23Test {
    @Test
    fun `Should compute longest path`() {
        longestScenicRoute(readInput("day23-sample")) shouldBe 94
    }

    @Test
    fun `Should compute longest path without slopes`() {
        longestScenicRouteWithoutSlopes(readInput("day23-sample")) shouldBe 154
    }

}
