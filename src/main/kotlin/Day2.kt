import kotlin.math.sign

object Day2 : AOC(2) {
    override fun part1(input: String): Int = parseReports(input)
        .count(::isSafe)

    override fun part2(input: String): Int = parseReports(input)
        .count(::isSafeDampened)

    private fun parseReports(input: String): List<Report> = input
        .lines()
        .map { it.words().map(String::toInt) }

    private fun isConsistent(report: Report): Boolean = report
        .asSequence()
        .zipWithNext(Int::compareTo)
        .map(Int::sign)
        .zipWithNext { a, b -> a != 0 && a == b }
        .all { it }

    private fun isSmooth(report: Report) = report
        .zipWithNext(::distance)
        .all((1..3)::contains)

    private fun isSafe(report: Report): Boolean = isSmooth(report) && isConsistent(report)

    private fun isSafeDampened(report: Report): Boolean = report
        .indices.asSequence()
        .map(report::skipAt)
        .any(::isSafe)
}

private typealias Report = List<Int>