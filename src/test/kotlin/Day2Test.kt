import kotlin.test.Test
import kotlin.test.assertEquals

class Day2Test {

    val input = readTestInput(2)

    @Test
    fun part1Test() {
        assertEquals(2, Day2.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(4, Day2.part2(input))
    }
}