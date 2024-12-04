object Day4 : AOC(4) {
    override fun part1(input: String): Int {
        val mat = parseInput(input)
        val slopes = listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0, 1 to 1, 1 to -1, -1 to 1, -1 to -1).map(::Offset)
        return mat.cells()
            .filter { it.value == 'X' }
            .sumOf { (pos, _) ->
                slopes.count { slope ->
                    mat.hasXmasAt(pos, slope)
                }
            }
    }

    override fun part2(input: String): Any {
        val mat = parseInput(input)
        return mat.cells().count { (pos, _) -> mat.hasMasCrossAt(pos) }
    }

    private fun parseInput(input: String) = Matrix(input.lines())

    private fun Matrix.hasMasCrossAt(pos: Offset): Boolean {
        fun hasMasAt(m: Offset): Boolean {
            val ms = (getOrNull(pos + m) == 'M' && getOrNull(pos - m) == 'S')
            val sm = (getOrNull(pos + m) == 'S' && getOrNull(pos - m) == 'M')
            return ms || sm
        }

        if (getOrNull(pos) != 'A') return false
        return hasMasAt(Offset(1, 1)) && hasMasAt(Offset(1, -1))
    }

    private tailrec fun Matrix.hasXmasAt(pos: Offset, slope: Offset, lookingFor: String = "XMAS"): Boolean =
        when {
            lookingFor.length == 1 -> getOrNull(pos) == lookingFor.first()
            getOrNull(pos) != lookingFor.first() -> false
            else -> hasXmasAt(pos + slope, slope, lookingFor.drop(1))
        }
}

private data class Offset(val x: Int, val y: Int) {
    constructor(p: Pair<Int, Int>) : this(p.first, p.second)

    operator fun plus(other: Offset) = Offset(x + other.x, y + other.y)
    operator fun minus(other: Offset) = Offset(x - other.x, y - other.y)
}

private class Matrix(
    val lines: List<String>
) {
    data class Cell(val pos: Offset, val value: Char)

    fun getOrNull(at: Offset) = lines.getOrNull(at.y)?.getOrNull(at.x)

    fun cells(): Sequence<Cell> = sequence {
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, ch ->
                yield(Cell(Offset(x, y), ch))
            }
        }
    }
}