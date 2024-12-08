import java.io.File
import kotlin.math.abs

data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position) = Position(
        this.x + other.x,
        this.y + other.y
    )

    operator fun minus(other: Position) = Position(
        this.x - other.x,
        this.y - other.y
    )

    fun inBoundsOf(width: Int, height: Int): Boolean = x in 0..<width && y in 0..<height
}

val inputDir = File("src/main/resources/input")

fun readPuzzleInput(day: Int) = File(inputDir, "day$day.txt").readText()

fun String.words() = split(Regex("\\s+"))

fun distance(a: Int, b: Int): Int = abs(a - b)

fun <T> List<T>.skipAt(index: Int): List<T> = slice((0..<index) + ((index + 1)..<size))

fun <T> List<List<T>>.transpose(): List<List<T>> =
    (this[0].indices).map { i -> (this.indices).map { j -> this[j][i] } }