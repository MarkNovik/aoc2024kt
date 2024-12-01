import java.io.File

val inputDir = File("src/main/resources/input")

fun readInputFile(day: Int) = File(inputDir, "day$day.txt").readText()
