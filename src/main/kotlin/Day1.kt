object Day1 : AOC(1) {
    override fun part1(input: String): Int = parseInput(input)
        .map(List<Int>::sorted)
        .transpose()
        .sumOf { it.reduce(::distance) }

    override fun part2(input: String): Int {
        val (left, b) = parseInput(input)
        val right = b.groupingBy { it }.eachCount().withDefault { 0 }
        return left.sumOf { it * right.getValue(it) }
    }

    private fun parseInput(input: String): List<List<Int>> = input
        .lines()
        .map { it.words().map(String::toInt) }
        .transpose()
}