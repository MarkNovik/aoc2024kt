import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day14Test {
    private val input = readTestInput(14)
    @Test
    fun part1Test() {
        Day14.width = 11
        Day14.height = 7
        assertEquals(12, Day14.part1(input))
    }
}