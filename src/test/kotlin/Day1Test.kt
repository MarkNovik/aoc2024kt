import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {

    val input = readTestInput(1)

    @Test
    fun part1Test() {
        assertEquals(11, Day1.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(31, Day1.part2(input))
    }
}