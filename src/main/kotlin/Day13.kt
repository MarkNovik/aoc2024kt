import java.math.BigInteger

object Day13 : AOC(13) {
    override fun part1(input: String): BigInteger = parseInput(input, BigInteger.ZERO)
        .mapNotNull(Arcade::cheapestWin)
        .fold(BigInteger.ZERO, BigInteger::plus)

    override fun part2(input: String): Any = "TODO"


    fun parseInput(input: String, offset: BigInteger): Sequence<Arcade> =
        Regex("""Button\sA:\sX\+(\d+),\sY\+(\d+)\nButton\sB:\sX\+(\d+),\sY\+(\d+)\nPrize:\sX=(\d+),\sY=(\d+)""")
            .findAll(input)
            .map {
                val (ax, ay, bx, by, px, py) = it.destructured
                Arcade(
                    a = BigPosition(
                        x = ax.toBigInteger(),
                        y = ay.toBigInteger()
                    ),
                    b = BigPosition(
                        x = bx.toBigInteger(),
                        y = by.toBigInteger()
                    ),
                    prize = BigPosition(
                        x = px.toBigInteger() + offset,
                        y = py.toBigInteger() + offset
                    )
                )
            }

}

operator fun BigPosition.div(other: BigPosition): BigInteger? {
    val x = (this.x / other.x).takeIf { this.x % other.x == BigInteger.ZERO } ?: return null
    val y = (this.y / other.y).takeIf { this.y % other.y == BigInteger.ZERO } ?: return null
    return if (x == y) x else null
}

data class Arcade(
    val a: BigPosition,
    val b: BigPosition,
    val prize: BigPosition
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