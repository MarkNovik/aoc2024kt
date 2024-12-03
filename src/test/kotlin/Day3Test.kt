import kotlin.test.Test
import kotlin.test.assertEquals

class Day3Test {

    @Test
    fun part1Test() {
        val input = readTestInput(301)
        assertEquals(161, Day3.part1(input))
    }

    @Test
    fun part2Test() {
        val input = readTestInput(302)
        assertEquals(48, Day3.part2(input))
    }
}