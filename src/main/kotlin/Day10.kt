object Day10 : AOC(10) {
    override fun part1(input: String): Int = solve(input, ::mutableSetOf)

    override fun part2(input: String): Int = solve(input, ::mutableListOf)

    private fun solve(input: String, acc: () -> MutableCollection<Vec2>): Int {
        val map = parseInput(input)
        return map.withIndex().sumOf { (y, line) ->
            line.withIndex().sumOf { (x, height) ->
                if (height == 0)
                    trailDestinations(acc(), map, Vec2(x, y)).size else 0
            }
        }
    }

    private fun parseInput(input: String): List<List<Int>> = input
        .lines()
        .map { it.map(Char::digitToInt) }

    private fun <C : MutableCollection<Vec2>> trailDestinations(
        to: C,
        at: List<List<Int>>,
        from: Vec2,
        currentHeight: Int = at[from],
    ): C =
        if (currentHeight == 9) to.apply { add(from) }
        else to.also {
            for (o in Offset.straight) if (at.getOrNull(from + o) == currentHeight + 1)
                trailDestinations(to, at, from + o)
        }
}