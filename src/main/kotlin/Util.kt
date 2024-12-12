import java.io.File
import kotlin.math.abs

data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position) = Position(
        this.x + other.x,
        this.y + other.y
    )

    operator fun plus(o: Offset) = Position(
        x + o.dx,
        y + o.dy
    )

    operator fun minus(other: Position) = Position(
        this.x - other.x,
        this.y - other.y
    )

    operator fun minus(o: Offset) = Position(
        x - o.dx,
        y - o.dy
    )

    fun inBoundsOf(width: Int, height: Int): Boolean = x in 0..<width && y in 0..<height

    override fun toString(): String = "($x, $y)"
}

enum class Offset(
    val dx: Int,
    val dy: Int
) {
    Up(0, -1),
    UpRight(1, -1),
    Right(1, 0),
    DownRight(1, 1),
    Down(0, 1),
    DownLeft(-1, 1),
    Left(-1, 0),
    UpLeft(-1, -1);

    companion object {
        val straight = listOf(Up, Right, Down, Left)
    }
}

val inputDir = File("src/main/resources/input")

fun readPuzzleInput(day: Int) = File(inputDir, "day$day.txt").readText()

fun String.words() = split(Regex("\\s+"))

fun distance(a: Int, b: Int): Int = abs(a - b)

fun <T> List<T>.skipAt(index: Int): List<T> = slice((0..<index) + ((index + 1)..<size))

fun <T> List<List<T>>.transpose(): List<List<T>> =
    (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }

fun <T> List<T>.chopFirst() = first() to drop(1)

operator fun <T> List<List<T>>.get(pos: Position) = this[pos.y][pos.x]

fun <T> List<List<T>>.getOrNull(pos: Position): T? = getOrNull(pos.y)?.getOrNull(pos.x)

fun IntArray.swap(i: Int, j: Int) {
    require(i in indices)
    require(j in indices)
    val temp = get(i)
    set(i, get(j))
    set(j, temp)
}