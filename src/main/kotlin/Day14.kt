object Day14 : AOC(14) {
    var width = 101
    var height = 103

    override fun part1(input: String): Int = parseInput(input)
        .map { it.move(100, width, height) }
        .groupingBy { it.quadrant(width, height) }
        .eachCount()
        .filter { it.key != null }
        .values.reduce(Int::times)


    override fun part2(input: String): Any = "TODO"

    private fun parseInput(input: String) = Regex("""p=(\d+),(\d+)\sv=(-?\d+),(-?\d+)""")
        .findAll(input)
        .map {
            val (px, py, vx, vy) = it.destructured
            SecurityRobot(
                pos = Vec2(
                    px.toInt(),
                    py.toInt()
                ),
                velocity = Vec2(
                    vx.toInt(),
                    vy.toInt()
                )
            )
        }.toList()
}

private data class SecurityRobot(
    val pos: Vec2,
    val velocity: Vec2
) {
    fun move(times: Int, width: Int, height: Int) = SecurityRobot(
        Vec2(
            (pos.x + velocity.x * times).mod(width),
            (pos.y + velocity.y * times).mod(height),
        ), velocity
    )

    fun quadrant(width: Int, height: Int): Int? =
        if (pos.x == width / 2 || pos.y == height / 2) null
        else (pos.y > height / 2).toInt().shl(1) + (pos.x > width / 2).toInt()
}