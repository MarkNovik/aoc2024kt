import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15Test {

    private val bigInput = readTestInput(15)
    private val smallInput = readTestInput(151)

    @Test fun part1BigTest() = assertEquals(10092, Day15.part1(bigInput))
    @Test fun part1SmallTest() = assertEquals(2028, Day15.part1(smallInput))

    private val thirdInput = """#######
#...#.#
#.....#
#..OO@#
#..O..#
#.....#
#######

<vv<<^^<<^^"""

    @Test fun part2Test() {
        println(Day15.part2(thirdInput))
        //assert(false)
        assertEquals(9021, Day15.part2(bigInput))
    }
}