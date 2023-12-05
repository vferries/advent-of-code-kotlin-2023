import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {
    @Test
    fun `Should parse ranges correctly`() {
        val input = readInput("day05-sample")
        val (seeds, maps) = parseAlmanach(input)
        seeds shouldBe listOf(79, 14, 55, 13)
        maps.getValue("seed" to "soil") shouldBe listOf(
            Mapping(source = 98, destination = 50, rangeLength = 2),
            Mapping(source = 50, destination = 52, rangeLength = 48)
        )
    }

    @Test
    fun `Should find correct mapping for first seed`() {
        val input = readInput("day05-sample")
        val (seeds, maps) = parseAlmanach(input)
        seedToLocation(seeds[0], maps) shouldBe 82
    }

    @Test
    fun `Should find correct sample answer`() {
        val input = readInput("day05-sample")
        val (seeds, maps) = parseAlmanach(input)
        seeds.minOfOrNull { seedToLocation(it, maps) } shouldBe 35
    }

    @Test
    fun `Should find correct sample answer for part 2`() {
        val input = readInput("day05-sample")
        val (seeds, maps) = parseAlmanach(input)
        val pairs = seeds.chunked(2)
        pairs.minOfOrNull { bestLocation(it[0]..<it[0] + it[1], maps) } shouldBe 46
    }
}

