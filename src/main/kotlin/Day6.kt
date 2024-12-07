object Day6 : AOC(6) {
    override fun part1(input: String): Int {
        val (map, guard) = parseInput(input)
        return generateSequence(guard) { it.advance(map) }.distinctBy(Guard::pos).count()
    }

    override fun part2(input: String): Int {
        return -1
    }

    private fun parseInput(input: String): Pair<TerritoryMap, Guard> {
        var guard: Guard? = null
        return input.lines().mapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                when (ch) {
                    '^' -> {
                        guard = Guard(Position(x, y))
                        Cell.Space
                    }

                    '#' -> Cell.Obstacle
                    else -> Cell.Space
                }
            }
        }.let(::TerritoryMap) to (guard ?: error("No guard position found"))
    }
}

private data class Position(val x: Int, val y: Int)

private enum class Cell { Space, Obstacle }

private enum class Direction(val dx: Int, val dy: Int) {
    Up(0, -1),
    Right(1, 0),
    Down(0, 1),
    Left(-1, 0);

    fun next() = entries[(ordinal + 1) % entries.size]
}

private class TerritoryMap(val cells: List<List<Cell>>) {

    fun getOrNull(x: Int, y: Int): Cell? = cells.getOrNull(y)?.getOrNull(x)

    @Suppress("unused")
    fun replace(pos: Position, replacement: Cell): TerritoryMap? =
        if (pos.y in cells.indices && pos.x in cells[pos.y].indices)
            TerritoryMap(cells.mapIndexed { y, line ->
                line.mapIndexed { x, cell ->
                    if (Position(
                            x,
                            y
                        ) == pos
                    ) replacement else cell
                }
            })
        else null

}


private data class Guard(val pos: Position, val dir: Direction = Direction.Up) {
    fun rotate(): Guard = copy(
        dir = dir.next()
    )


    fun move() = copy(
        pos = targetPosition()
    )

    fun advance(map: TerritoryMap): Guard? {
        val (tx, ty) = targetPosition()
        return when (map.getOrNull(tx, ty) ?: return null) {
            Cell.Space -> move()
            Cell.Obstacle -> rotate()
        }
    }

    fun targetPosition(): Position = Position(pos.x + dir.dx, pos.y + dir.dy)
}