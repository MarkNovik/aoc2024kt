import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day5Test {

    val input = readTestInput(5)

    @Test
    fun part1Test() {
        assertEquals(143, Day5.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(123, Day5.part2(input))
    }
}