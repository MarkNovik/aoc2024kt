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

    @Test
    fun realInputTest() {
        val realInput = readPuzzleInput(7)
        assertEquals(4555081946288UL, Day7.part1(realInput))
        assertEquals(227921760109726UL, Day7.part2(realInput))
    }
}