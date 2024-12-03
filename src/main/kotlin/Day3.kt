object Day3 : AOC(3) {
    sealed interface Command {
        object Do : Command
        object Dont : Command
        data class Mul(val a: Long, val b: Long) : Command {
            val product get() = a * b
        }
    }

    override fun part1(input: String): Long = parseMul(input)
        .sumOf(Command.Mul::product)

    override fun part2(input: String): Long = parseCommands(input)
        .fold(0L to true) { (acc, active), com ->
            when (com) {
                Command.Do -> acc to true
                Command.Dont -> acc to false
                is Command.Mul -> Pair(
                    if (active) acc + com.a * com.b else acc,
                    active
                )
            }
        }.first

    fun parseMul(input: String): Sequence<Command.Mul> =
        Regex("""mul\((\d+),(\d+)\)""")
            .findAll(input)
            .map {
                val (a, b) = it.destructured
                Command.Mul(a.toLong(), b.toLong())
            }

    fun parseCommands(input: String): Sequence<Command> =
        Regex("""(do|don't|mul)\((?:(\d+),(\d+))?\)""")
            .findAll(input)
            .map {
                val (name, a, b) = it.destructured
                when (name) {
                    "do" -> Command.Do
                    "don't" -> Command.Dont
                    "mul" -> Command.Mul(a.toLong(), b.toLong())
                    else -> error("Invalid name `$name` at ${it.range}")
                }
            }
}