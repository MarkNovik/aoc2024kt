import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day4Test {

    private val input = readTestInput(4)

    @Test
    fun part1Test() {
        assertEquals(18, Day4.part1(input))
    }

    @Test
    fun part2Test() {
        assertEquals(9, Day4.part2(input))
    }
}