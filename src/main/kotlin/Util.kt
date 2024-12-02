import java.io.File
import kotlin.math.abs

val inputDir = File("src/main/resources/input")

fun readPuzzleInput(day: Int) = File(inputDir, "day$day.txt").readText()

fun String.words() = split(Regex("\\s+"))

fun distance(a: Int, b: Int): Int = abs(a - b)

fun <T> List<T>.skipAt(index: Int): List<T> =
    slice((0..<index) + ((index + 1)..<size))