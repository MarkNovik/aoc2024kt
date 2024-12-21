object Day6 : AOC(6) {
    override fun part1(input: String): Int {
        val (map, guard) = parseInput(input)
        return generateSequence(guard) { it.advance(map) }.distinctBy(Guard::pos).count()
    }

    override fun part2(input: String): Long {
        val (map, guard) = parseInput(input)

        return (generateSequence(guard) { it.advance(map) }.map(Guard::pos).toSet() - guard.pos)
            .parallelStream()
            .filter { guard.loopsIfPlaceObstacleAt(it, map) }
            .count()
    }

    private fun parseInput(input: String): Pair<TerritoryMap, Guard> {
        var guard: Guard? = null
        return input.lines().mapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                when (ch) {
                    '^' -> {
                        guard = Guard(Vec2(x, y))
                        Cell.Space
                    }

                    '#' -> Cell.Obstacle
                    else -> Cell.Space
                }
            }
        }.let(::TerritoryMap) to (guard ?: error("No guard position found"))
    }
}

private enum class Cell { Space, Obstacle }

private enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1),
    Right(1, 0),
    Down(0, 1),
    Left(-1, 0);

    fun next(times: Int = 1) = entries[(ordinal + times) % entries.size]
}

private data class TerritoryMap(val width: Int, val height: Int, val obstacles: Set<Vec2>) {
    constructor(cells: List<List<Cell>>) : this(
        cells.first().size,
        cells.size,
        cells.flatMapIndexed { y: Int, line: List<Cell> ->
            line.mapIndexedNotNull { x, cell ->
                Vec2(
                    x,
                    y
                ).takeIf { cell == Cell.Obstacle }
            }
        }.toSet()
    )

    operator fun contains(pos: Vec2) = pos.y in 0..<height && pos.x in 0..<width

    fun getOrNull(pos: Vec2): Cell? = when (pos) {
        !in this -> null
        in obstacles -> Cell.Obstacle
        else -> Cell.Space
    }

    fun withObstacleAt(pos: Vec2): TerritoryMap? =
        if (pos in this) copy(obstacles = obstacles + pos)
        else null

}


private data class Guard(val pos: Vec2, val dir: Direction = Direction.Up) {
    fun rotate(times: Int = 1): Guard = copy(
        dir = dir.next(times)
    )

    fun move() = copy(
        pos = targetPosition()
    )

    fun advance(map: TerritoryMap): Guard? =
        when (map.getOrNull(targetPosition())) {
            null -> null
            Cell.Space -> move()
            Cell.Obstacle -> rotate()
        }

    fun loopsIfPlaceObstacleAt(pos: Vec2, map: TerritoryMap): Boolean {
        val test = map.withObstacleAt(pos) ?: return false
        val visited = mutableSetOf<Guard>()
        return generateSequence(this) { it.advance(test) }.any { !visited.add(it) }
    }

    fun targetPosition(): Vec2 = Vec2(pos.x + dir.dx, pos.y + dir.dy)
}