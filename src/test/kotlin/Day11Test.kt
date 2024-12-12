import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    private val input = "125 17"

    @Test
    fun part1Test() {
        assertEquals(55312L, Day11.part1(input))
    }
}