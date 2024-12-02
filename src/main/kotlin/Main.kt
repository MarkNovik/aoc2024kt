import kotlin.time.measureTimedValue

fun main() {
    val day1 = readPuzzleInput(1)
    println("Day 1:")
    println("\tPart 1: ${measureTimedValue { Day1.part1(day1) }}")
    println("\tPart 2: ${measureTimedValue { Day1.part2(day1) }}")
    val day2 = readPuzzleInput(2)
    println("Day 2")
    println("\tPart 1: ${measureTimedValue { Day2.part1(day2) }}")
    println("\tPart 2: ${measureTimedValue { Day2.part2(day2) }}")
}