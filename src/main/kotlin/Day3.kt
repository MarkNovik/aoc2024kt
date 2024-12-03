object Day3 : AOC(3) {
    sealed interface Command {
        object Do : Command
        object Dont : Command
        data class Mul(val a: Long, val b: Long) : Command {
            val product get() = a * b
        }
    }

    override fun part1(input: String): Long = parseInput(input)
        .filterIsInstance<Command.Mul>()
        .sumOf(Command.Mul::product)

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

    fun parseInput(input: String): Sequence<Command> =
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