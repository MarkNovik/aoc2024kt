import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day7Test {

    private val input = readTestInput(7)

    @Test
    fun part1Test() {
        assertEquals(3749UL, Day7.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(11387UL, Day7.part2(input))
    }
}