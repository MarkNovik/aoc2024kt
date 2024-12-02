object Day1 {
    fun part1(input: String): Int {
        val (left, right) = parseInput(input)
        return left.sorted().zip(right.sorted(), ::distance).sum()
    }

    fun part2(input: String): Int {
        val (left, b) = parseInput(input)
        val right = b.groupingBy { it }.eachCount()
        return left.sumOf { it * right.getOrDefault(it, 0) }
    }

    private fun parseInput(input: String): Pair<List<Int>, List<Int>> = input
        .lines()
        .map {
            val (a, b) = it.words().map(String::toInt)
            a to b
        }.unzip()
}