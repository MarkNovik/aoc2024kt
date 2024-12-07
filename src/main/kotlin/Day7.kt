import java.util.stream.Stream

private typealias Op = (ULong, ULong) -> ULong

object Day7 : AOC(7) {
    override fun part1(input: String): ULong =
        parseInput(input)
            .parallelStream()
            .filter { (res, elements) ->
                res.canBeMadeWith(
                    elements,
                    listOf<Op>(ULong::plus, ULong::times)
                )
            }
            .sumOf { it.first }


    override fun part2(input: String): ULong =
        parseInput(input)
            .parallelStream()
            .filter { (res, elements) ->
                res.canBeMadeWith(
                    elements,
                    listOf<Op>(ULong::plus, ULong::times, ULong::concat)
                )
            }
            .sumOf { it.first }

    private fun parseInput(input: String): List<Pair<ULong, List<ULong>>> = input.lines().map {
        val (res, elements) = it.split(":")
        res.trim().toULong() to elements.trim().split(Regex("\\D+")).map(String::toULong)
    }
}

private inline fun <reified T> Stream<T>.sumOf(noinline f: (T) -> ULong): ULong =
    map(f).reduce(0UL, ULong::plus)

private fun ULong.concat(other: ULong): ULong = (this.toString() + other.toString()).toULong()
private fun ULong.canBeMadeWith(elements: List<ULong>, operations: List<Op>, acc: ULong = 0UL): Boolean = when {
    elements.isEmpty() -> this == acc
    else -> {
        val head = elements.first()
        val tail = elements.drop(1)
        operations.any {
            canBeMadeWith(tail, operations, it(acc, head))
        }
    }
}