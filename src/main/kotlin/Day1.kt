object Day1: AOC(1) {
    override fun part1(input: String): Int {
        val (left, right) = parseInput(input)
        return left.sorted().zip(right.sorted(), ::distance).sum()
    }

    override fun part2(input: String): Int {
        val (left, b) = parseInput(input)
        val right = b.groupingBy { it }.eachCount().withDefault { 0 }
        return left.sumOf { it * right.getValue(it) }
    }

    private fun parseInput(input: String): Pair<List<Int>, List<Int>> = input
        .lines()
        .map {
            val (a, b) = it.words().map(String::toInt)
            a to b
        }.unzip()
}