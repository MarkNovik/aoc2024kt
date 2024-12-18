import kotlin.math.pow

object Day11 : AOC(11) {
    override fun part1(input: String): Long {
        val stones = parseInput(input)
        val cache = mutableMapOf<Pair<Long, UInt>, Long>()
        return stones.fold(0L) { acc, stone ->
            acc + blink(cache, stone, 25u)
        }
    }

    override fun part2(input: String): Long {
        val stones = parseInput(input)
        val cache = mutableMapOf<Pair<Long, UInt>, Long>()
        return stones.fold(0L) { acc, stone ->
            acc + blink(cache, stone, 75u)
        }
    }

    private fun blink(
        cache: MutableMap<Pair<Long, UInt>, Long>,
        stone: Long,
        stopAt: UInt,
        blinks: UInt = 0u,
    ): Long =
        if (blinks >= stopAt) 1
        else cache.getOrPut(stone to blinks) {
            if (stone == 0L) blink(cache, 1, stopAt, blinks + 1u)
            else {
                val len = stone.length()
                if (len % 2 == 0) {
                    val divisor = 10.0.pow(len / 2).toLong()
                    val left = stone / divisor
                    val right = stone % divisor
                    blink(cache, left, stopAt, blinks + 1u) + blink(cache, right, stopAt, blinks + 1u)
                } else blink(cache, stone * 2024, stopAt, blinks + 1u)
            }
        }

    private fun parseInput(input: String) = input.words().map(String::toLong)
}

private tailrec fun Long.length(acc: Int = 0): Int =
    if (this == 0L) acc
    else div(10L).length(acc + 1)