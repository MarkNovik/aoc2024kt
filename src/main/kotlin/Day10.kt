object Day10 : AOC(10) {
    override fun part1(input: String): Int {
        val map = parseInput(input)
        return map.withIndex().sumOf { (y, line) ->
            line.withIndex().sumOf { (x, height) ->
                if (height == 0)
                    trailDestinations(map, Position(x, y)).distinct().size else 0
            }
        }
    }

    override fun part2(input: String): Int {
        val map = parseInput(input)
        return map.withIndex().sumOf { (y, line) ->
            line.withIndex().sumOf { (x, height) ->
                if (height == 0)
                    trailDestinations(map, Position(x, y)).size else 0
            }
        }
    }

    private fun parseInput(input: String): List<List<Int>> = input
        .lines()
        .map { it.map(Char::digitToInt) }

    private fun trailDestinations(
        at: List<List<Int>>,
        from: Position,
        currentHeight: Int = at[from],
    ): List<Position> =
        if (currentHeight == 9) listOf(from)
        else buildList {
            for (o in Offset.straight) if (at.getOrNull(from + o) == currentHeight + 1)
                addAll(trailDestinations(at, from + o))
        }
}