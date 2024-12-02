object Day1 {
    fun part1(input: String): Int {
        val (left, right) = parseInput(input)
        return left.sorted().zip(right.sorted(), ::distance).sum()
    }

    fun part2(input: String): Int {
        val (left, b) = parseInput(input)
        val right = b.associateWith { b.count(it::equals) }
        return left.sumOf { it * (right[it] ?: 0) }
    }

    private fun parseInput(input: String): Pair<List<Int>, List<Int>> =
        input.lines()
            .fold(emptyList<Int>() to emptyList<Int>()) { (l, r), next ->
                val (a, b) = next.words().map(String::toInt)
                (l + a) to (r + b)
            }
}