import arrow.core.compose as on

object Day15 : AOC(15) {
    override fun part1(input: String): Int = solve(input, ::parseInputRegular)

    override fun part2(input: String): Int = solve(input, ::parseInputWide)

    private inline fun solve(input: String, parser: (String) -> Triple<Sea, List<Offset>, Sea.Submarine>): Int {
        val (sea, moves, submarine) = parser(input)
        moves.forEach {
            sea.move(submarine, it)
        }
        return sea.entities
            .filterIsInstance<Sea.Box>()
            .sumOf(Sea.Box::gps)
    }

    private fun parseInputRegular(input: String) = parseInputGeneric(input, ::parseSeaRegular)

    private fun parseInputWide(input: String) = parseInputGeneric(input, ::parseSeaWide)

    private inline fun parseInputGeneric(
        input: String,
        seaParser: (List<String>) -> Sea
    ): Triple<Sea, List<Offset>, Sea.Submarine> {
        val (map, moves) = input.split(System.lineSeparator().repeat(2)).toPair().mapFirst(String::words)
        val sea = seaParser(map)
        return Triple(sea, parseMoves(moves), sea.entities.singleInstanceOf<Sea.Submarine>())
    }

    private fun parseSeaRegular(map: List<String>): Sea {
        val height = map.size
        val width = map[0].length
        val entities = map.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                val pos = Vec2(x, y)
                when (c) {
                    '#' -> Sea.Wall(pos)
                    '@' -> Sea.Submarine(pos)
                    'O' -> Sea.SmallBox(pos)
                    else -> null
                }
            }
        }.toSet()
        return Sea(width, height, entities)
    }

    private fun parseSeaWide(map: List<String>): Sea {
        val height = map.size
        val width = map[0].length * 2
        val entities = map.flatMapIndexed { y, line ->
            line.flatMapIndexed { x, c ->
                val pos = Vec2(x * 2, y)
                when (c) {
                    '#' -> listOf(Sea.Wall(pos), Sea.Wall(pos.copy(x = pos.x + 1)))
                    '@' -> listOf(Sea.Submarine(pos))
                    'O' -> Sea.BigBoxLeft(pos).let { listOf(it, it.right) }
                    else -> emptyList()
                }
            }
        }.toSet()
        return Sea(width, height, entities)
    }

    private fun parseMoves(moves: String): List<Offset> =
        moves.mapNotNull {
            when (it) {
                '^' -> Offset.Up
                '>' -> Offset.Right
                'v' -> Offset.Down
                '<' -> Offset.Left
                else -> null
            }
        }
}

private class Sea(val width: Int, val height: Int, val entities: Set<Entity>) {
    fun move(entity: Entity, dir: Offset, checked: Set<Entity> = emptySet()): Boolean {
        val targetPos = entity.pos + dir
        if (targetPos.outOfBoundsOf(width, height)) return false
        val next = entities.find(targetPos::equals on Entity::pos)
        if (next in checked) return true
        return when (next) {
            is Wall -> false
            is BigBoxRight -> if (canMove(next, dir) && move(next.left, dir, checked + next)) {
                move(next, dir)
                entity.pos = targetPos
                true
            } else false

            is BigBoxLeft -> if (canMove(next, dir) && move(next.right, dir, checked + next)) {
                move(next, dir)
                entity.pos = targetPos
                true
            } else false

            null -> {
                entity.pos = targetPos
                true
            }

            else -> if (move(next, dir)) {
                entity.pos = targetPos
                true
            } else false
        }
    }

    fun canMove(entity: Entity, dir: Offset, checked: Set<Entity> = emptySet()): Boolean {
        val targetPos = entity.pos + dir
        if (targetPos.outOfBoundsOf(width, height)) return false
        val next = entities.find(targetPos::equals on Entity::pos)
        if (next != null && next in checked) return true
        return when (next) {
            null -> true
            is Wall -> false
            is BigBoxRight -> canMove(next, dir) && canMove(next.left, dir, checked + next)
            is BigBoxLeft -> canMove(next, dir) && canMove(next.right, dir, checked + next)
            else -> canMove(next, dir)
        }
    }

    sealed interface Entity {
        var pos: Vec2
    }

    class Submarine(override var pos: Vec2) : Entity
    class Wall(override var pos: Vec2) : Entity

    sealed interface Box : Entity {
        val gps get() = pos.y * 100 + pos.x
    }

    class SmallBox(override var pos: Vec2) : Box

    class BigBoxLeft(override var pos: Vec2) : Box {
        val right = BigBoxRight(pos.copy(x = pos.x + 1), this)
    }
    class BigBoxRight(override var pos: Vec2, val left: BigBoxLeft) : Box {
        override val gps: Int = 0
    }
}