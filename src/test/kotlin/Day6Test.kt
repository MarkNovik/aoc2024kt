import kotlin.test.Test
import kotlin.test.assertEquals

class Day6Test {

    private val input = readTestInput(6)

    @Test
    fun part1Test() {
        assertEquals(41, Day6.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(6, Day6.part2(input))
    }
}