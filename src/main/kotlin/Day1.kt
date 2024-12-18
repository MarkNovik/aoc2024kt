import arrow.core.compose
import arrow.core.partially2

object Day1 : AOC(1) {
    override fun part1(input: String): Int = parseInput(input)
        .map(List<Int>::sorted)
        .transpose()
        .sumOf(List<Int>::reduce.partially2(::distance))

    override fun part2(input: String): Int {
        val (left, b) = parseInput(input)
        val right = b.groupingBy { it }.eachCount().withDefault { 0 }
        return left.sumOf { it * right.getValue(it) }
    }

    private fun parseInput(input: String): List<List<Int>> = input
        .lines()
        .map(specify<_, LMap<String, Int>>(List<String>::map).partially2(String::toInt) compose String::words)
        .transpose()
}