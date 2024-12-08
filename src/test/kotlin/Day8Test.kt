import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day8Test {

    private val input = readTestInput(8)

    @Test
    fun part1Test() {
        assertEquals(14, Day8.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(34, Day8.part2(input))
    }
}