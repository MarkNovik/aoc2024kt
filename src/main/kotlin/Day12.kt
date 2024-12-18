object Day12 : AOC(12) {
    override fun part1(input: String): Int =
        /*
        times(area, perimeter)
         */
        separateRegions(parseInput(input))
            .sumOf { it.area * it.perimeter }

    override fun part2(input: String): String = "TODO"

    fun parseInput(input: String) = input
        .lines()
        .filterNot(String::isEmpty)
        .map(String::toList)

    fun separateRegions(map: List<List<Char>>): Set<Region> {
        val visited = List(map.size) { MutableList(map[0].size) { false } }
        val regions = mutableSetOf<Region>()

        fun locateRegion(pos: Vec2, plant: Char, carets: MutableSet<Vec2>) {
            if (!pos.inBoundsOf(map.first().size, map.size) || visited[pos] || map[pos] != plant) return

            visited[pos] = true
            carets.add(pos)

            for (direction in Offset.straight) {
                val next = pos + direction
                locateRegion(next, plant, carets)
            }
        }

        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, plant ->
                if (!visited[y][x]) {
                    val carets = mutableSetOf<Vec2>()
                    locateRegion(Vec2(x, y), plant, carets)
                    regions.add(carets)
                }
            }
        }

        return regions
    }
}

private typealias Region = Set<Vec2>

private val Region.area: Int get() = size
private val Region.perimeter: Int
    get() = sumOf { v ->
        Offset.straight.count {
            (v + it) !in this
        }
    }