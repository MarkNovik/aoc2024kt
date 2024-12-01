import kotlin.test.Test
import kotlin.test.assertEquals

class Day1Test {
    @Test
    fun testPart1() {
        val input = readTestInput(1)
        val res = Day1.part1(input)
        assertEquals(11, res)
    }
}