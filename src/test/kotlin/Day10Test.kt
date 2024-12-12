import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day10Test {

    private val input = readTestInput(10)

    @Test
    fun part1Test() {
        assertEquals(36, Day10.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(81, Day10.part2(input))
    }
}