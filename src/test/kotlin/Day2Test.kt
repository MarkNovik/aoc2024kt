import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {
    @Test
    fun part1Test() {
        val input = readTestInput(2)
        assertEquals(2, Day2.part1(input))
    }

    @Test
    fun part2Test() {
        val input = readTestInput(2)
        assertEquals(4, Day2.part2(input))
    }
}