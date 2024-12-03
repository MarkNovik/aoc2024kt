object Day3: AOC(3) {
    override fun part1(input: String): Long =
        Regex("""mul\((\d+),(\d+)\)""")
            .findAll(input)
            .sumOf {
                val (a, b) = it.destructured
                a.toLong() * b.toLong()
            }

    override fun part2(input: String): Long =
        Regex("""(do|don't|mul)\((?:(\d+),(\d+))?\)""")
            .findAll(input)
            .fold(0L to true) { (acc, active), next ->
                val (name, a, b) = next.destructured
                when (name) {
                    "do" -> acc to true
                    "don't" -> acc to false
                    "mul" if active -> (acc + a.toLong() * b.toLong()) to true
                    "mul" -> acc to false
                    else -> error("Invalid command `$name` at ${next.range}")
                }
            }.first

}