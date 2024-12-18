import java.util.stream.Stream

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
            .sumOf(Equation::first)


    override fun part2(input: String): ULong =
        parseInput(input)
            .parallelStream()
            .filter { (res, elements) ->
                res.canBeMadeWith(
                    elements,
                    listOf<Op>(ULong::plus, ULong::times, ::concat)
                )
            }
            .sumOf { it.first }

    private fun parseInput(input: String): List<Equation> = input.lines().map {
        val (res, elements) = it.split(":")
        res.trim().toULong() to elements.trim().split(Regex("\\D+")).map(String::toULong)
    }
}

private typealias Equation = Pair<ULong, List<ULong>>

private typealias Op = (ULong, ULong) -> ULong

private inline fun <reified T> Stream<T>.sumOf(noinline f: (T) -> ULong): ULong =
    map(f).reduce(0UL, ULong::plus)

private fun concat(a: ULong, b: ULong): ULong {
    var offset = 1UL
    while (offset <= b) offset *= 10UL
    return a * offset + b
}

private fun ULong.canBeMadeWith(elements: List<ULong>, operations: List<Op>, acc: ULong = 0UL): Boolean = when {
    elements.isEmpty() -> this == acc
    acc > this -> false
    else -> {
        val (head, tail) = elements.chopFirst()
        operations.any {
            canBeMadeWith(tail, operations, it(acc, head))
        }
    }
}