import java.math.BigInteger

object Day13 : AOC(13) {
    override fun part1(input: String): BigInteger = parseInput(input, BigInteger.ZERO)
        .mapNotNull(Arcade::cheapestWin)
        .fold(BigInteger.ZERO, BigInteger::plus)

    override fun part2(input: String): Any = "TODO"


    private fun parseInput(input: String, offset: BigInteger): Sequence<Arcade> =
        Regex("""Button\sA:\sX\+(\d+),\sY\+(\d+)\nButton\sB:\sX\+(\d+),\sY\+(\d+)\nPrize:\sX=(\d+),\sY=(\d+)""")
            .findAll(input)
            .map {
                val (ax, ay, bx, by, px, py) = it.destructured
                Arcade(
                    a = BigVec2(
                        x = ax.toBigInteger(),
                        y = ay.toBigInteger()
                    ),
                    b = BigVec2(
                        x = bx.toBigInteger(),
                        y = by.toBigInteger()
                    ),
                    prize = BigVec2(
                        x = px.toBigInteger() + offset,
                        y = py.toBigInteger() + offset
                    )
                )
            }

}

private operator fun BigVec2.div(other: BigVec2): BigInteger? {
    val x = (this.x / other.x).takeIf { this.x % other.x == BigInteger.ZERO } ?: return null
    val y = (this.y / other.y).takeIf { this.y % other.y == BigInteger.ZERO } ?: return null
    return if (x == y) x else null
}

private data class Arcade(
    val a: BigVec2,
    val b: BigVec2,
    val prize: BigVec2
) {
    fun cheapestWin(): BigInteger? {
        val bMax = minOf(prize.x / b.x, prize.y / b.y)
        return generateSequence(bMax, BigInteger::dec).takeWhile { it > BigInteger.ZERO }.asIterable()
            .minOfNotNullOrNull { bMoves ->
                val aMoves = ((prize - b * bMoves) / a)
                aMoves?.times(3.toBigInteger())?.plus(bMoves)
            }
    }
}