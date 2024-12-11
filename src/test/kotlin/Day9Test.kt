import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day9Test {

    private val input = readTestInput(9)

    @Test
    fun part1Test() {
        assertEquals(1928, Day9.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(2858, Day9.part2(input))
    }
}