import arrow.core.partially2

object Day5 : AOC(5) {
    override fun part1(input: String): Long {
        val (rules, updates) = parseInput(input)
        return updates.filter(Update::isCorrect.partially2(rules))
            .sumOf {
                it[it.size / 2]
            }
    }

    override fun part2(input: String): Long {
        val (rules, updates) = parseInput(input)
        return updates.filterNot { it.isCorrect(rules) }
            .sumOf { upd ->
                upd.sortedWith { a, b ->
                    when {
                        rules[a]?.contains(b) == true -> 1
                        rules[b]?.contains(a) == true -> -1
                        else -> 0
                    }
                }[upd.size / 2]
            }
    }

    private fun parseInput(input: String): Pair<Rules, List<Update>> {
        val (r, u) = input.split("\n\n")
        val rules = buildMap<Long, MutableSet<Long>> {
            r.lines().forEach { line ->
                val (before, after) = line.split('|').map(String::toLong)
                getOrPut(before) { mutableSetOf() } += after
            }
        }
        val updates = u.lines().map { it.split(',').map(String::toLong) }
        return rules to updates
    }
}

private typealias Update = List<Long>
private typealias Rules = Map<Long, Set<Long>>

private fun Update.isCorrect(rules: Rules): Boolean =
    rules.all { (k, v) ->
        val ki = indexOf(k)
        v.map(::indexOf).all {
            (it == -1 || ki == -1) || it > ki
        }
    }