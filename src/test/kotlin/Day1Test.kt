import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    @Test
    fun part1Test() {
        val input = readTestInput(1)
        assertEquals(11, Day1.part1(input))
    }

    @Test
    fun part2Test() {
        val input = readTestInput(1)
        assertEquals(31, Day1.part2(input))
    }
}