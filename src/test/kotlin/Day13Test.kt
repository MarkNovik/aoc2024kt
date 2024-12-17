import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day13Test {

    private val input = readTestInput(13)

    @Test
    fun part1Test() {
        assertEquals(480.toBigInteger(), Day13.part1(input))
    }
}