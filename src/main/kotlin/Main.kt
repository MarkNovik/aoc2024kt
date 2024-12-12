import kotlin.time.measureTimedValue

fun main() =
    AOC::class.sealedSubclasses.mapNotNull { it.objectInstance }.sortedBy { it.day }.forEach {
        val input = readPuzzleInput(it.day)
        println("Day ${it.day}:")
        println("\tPart 1: ${time { it.part1(input) }}")
        println("\tPart 2: ${time { it.part2(input) }}")
    }


inline fun <T> time(f: () -> T): String {
    val (value, dur) = measureTimedValue(f)
    return "$value, $dur"
}