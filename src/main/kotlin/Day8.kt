import com.github.michaelbull.itertools.pairCombinations
import arrow.core.partially2 as p2

object Day8 : AOC(8) {
    override fun part1(input: String): Int {
        val (width, height, map) = parseInput(input)
        return map.flatMap { (_, positions) ->
            positions.toList().pairCombinations().flatMap { (a, b) ->
                val m = b - a
                listOfNotNull(
                    (a - m).takeIf(Vec2::inBoundsOf.p2(width).p2(height)),
                    (b + m).takeIf(Vec2::inBoundsOf.p2(width).p2(height))
                )
            }
        }.toSet().count()
    }

    override fun part2(input: String): Int {
        val (width, height, map) = parseInput(input)
        return map.flatMap { (_, positions) ->
            positions.toList().pairCombinations().flatMap { (a, b) ->
                val m = b - a
                sequenceOf(
                    generateSequence(a, (-m)).takeWhile(Vec2::inBoundsOf.p2(width).p2(height)),
                    generateSequence(b, (+m)).takeWhile(Vec2::inBoundsOf.p2(width).p2(height))
                ).flatten()
            }
        }.toSet().count()
    }

    private fun parseInput(input: String): Triple<Int, Int, AntennaMap> {
        val height = input.lineSequence().count()
        val width = input.lineSequence().first().count()
        val antennas = input
            .lineSequence().flatMapIndexed { y: Int, line: String ->
                line.mapIndexedNotNull { x, c ->
                    if (c.isLetterOrDigit()) c to Vec2(x, y) else null
                }
            }.groupingBy { it.first }
            .aggregate { _, accumulator: MutableSet<Vec2>?, (_, pos), _ ->
                accumulator?.apply { add(pos) } ?: mutableSetOf(pos)
            }.toMap()
        return Triple(width, height, antennas)
    }
}

private typealias AntennaMap = Map<Char, Set<Vec2>>