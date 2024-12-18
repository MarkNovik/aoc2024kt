import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day12Test {

    private val input = readTestInput(12)

    @Test
    fun part1Test() {
        assertEquals(1930, Day12.part1(input))
    }
}