import kotlin.math.abs

object Day1 {
    fun part1(input: String): Int {
        val (a, b) = input.lines()
            .fold(emptyList<Int>() to emptyList<Int>()) { (l, r), next ->
                val (a, b) = next.split(Regex("\\s+")).map(String::toInt)
                (l + a) to (r + b)
            }
        return a.sorted().zip(b.sorted()) { a, b -> abs(a - b) }.sum()
    }
}

