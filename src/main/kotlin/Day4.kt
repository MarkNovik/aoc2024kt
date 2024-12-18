import arrow.core.compose
import arrow.core.partially1
import arrow.core.partially3

object Day4 : AOC(4) {
    override fun part1(input: String): Int {
        val mat = parseInput(input)
        return mat.cells()
            .filter('X'::equals compose Matrix.Cell::value)
            .sumOf { (pos, _) ->
                Offset.entries.count(mat::hasXmasAt.partially3("XMAS").partially1(pos))
            }
    }

    override fun part2(input: String): Any {
        val mat = parseInput(input)
        return mat.cells().count(mat::hasMasCrossAt compose Matrix.Cell::pos)
    }

    private fun parseInput(input: String) = Matrix(input.lines())
}

private class Matrix(
    val lines: List<String>
) {
    data class Cell(val pos: Vec2, val value: Char)

    fun getOrNull(at: Vec2) = lines.getOrNull(at.y)?.getOrNull(at.x)

    fun cells(): Sequence<Cell> = sequence {
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, ch ->
                yield(Cell(Vec2(x, y), ch))
            }
        }
    }

    tailrec fun hasXmasAt(pos: Vec2, slope: Offset, lookingFor: String = "XMAS"): Boolean =
        when {
            lookingFor.length == 1 -> getOrNull(pos) == lookingFor.first()
            getOrNull(pos) != lookingFor.first() -> false
            else -> hasXmasAt(pos + slope, slope, lookingFor.drop(1))
        }

    fun hasMasCrossAt(pos: Vec2): Boolean {
        fun hasMasAt(m: Offset): Boolean {
            val ms = (getOrNull(pos + m) == 'M' && getOrNull(pos - m) == 'S')
            val sm = (getOrNull(pos + m) == 'S' && getOrNull(pos - m) == 'M')
            return ms || sm
        }

        if (getOrNull(pos) != 'A') return false
        return hasMasAt(Offset.DownRight) && hasMasAt(Offset.UpRight)
    }
}