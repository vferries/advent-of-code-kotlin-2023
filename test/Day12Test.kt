import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun `Should compute arrangements`() {
        val sample = readInput("day12-sample")
        possibleArrangements(sample) shouldBe 21
    }

    @Test
    fun `Should compute arrangements part 2`() {
        val sample = readInput("day12-sample")
        possibleArrangementsPart2(sample) shouldBe 525152
    }

    @Test
    fun `Should compute valid arrangements for first line part 1`() {
        possibleArrangements(listOf("???.### 1,1,3")) shouldBe 1
    }

    @Test
    fun `Should compute valid arrangements for second line part 1`() {
        possibleArrangements(listOf(".??..??...?##. 1,1,3")) shouldBe 4
    }

    @Test
    fun `Should compute valid arrangements for first line`() {
        possibleArrangementsPart2(listOf("???.### 1,1,3")) shouldBe 1
    }

    @Test
    fun `Should compute valid arrangements for second line`() {
        possibleArrangementsPart2(listOf(".??..??...?##. 1,1,3")) shouldBe 16384
    }

    @Test
    fun `Should compute valid arrangements for third line`() {
        possibleArrangementsPart2(listOf("?#?#?#?#?#?#?#? 1,3,1,6")) shouldBe 1
    }

    @Test
    fun `Should compute valid arrangements for forth line`() {
        possibleArrangementsPart2(listOf("????.#...#... 4,1,1")) shouldBe 16
    }

    @Test
    fun `Should compute valid arrangements for fifth line`() {
        possibleArrangementsPart2(listOf("????.######..#####. 1,6,5")) shouldBe 2500
    }

    @Test
    fun `Should compute valid arrangements for sixth line`() {
        possibleArrangementsPart2(listOf("?###???????? 3,2,1")) shouldBe 506250
    }
}
