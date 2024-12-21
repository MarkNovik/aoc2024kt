import Sea.Entity.Type.*

object Day15 : AOC(15) {
    override fun part1(input: String): Int = solve(input, ::parseInputRegular)

    override fun part2(input: String): Int = solve(input, ::parseInputWide)

    private inline fun solve(input: String, parser: (String) -> Triple<Sea, List<Offset>, Sea.Entity>): Int {
        val (sea, moves, submarine) = parser(input)
        moves.forEach {
            sea.move(submarine, it)
        }
        return sea.entities
            .filter { it.type == SmallBox || it.type == BigBoxLeft }
            .sumOf(Sea.Entity::gps)
    }

    private fun parseInputRegular(input: String) = parseInputGeneric(input, ::parseSeaRegular)

    private fun parseInputWide(input: String) = parseInputGeneric(input, ::parseSeaWide)

    private inline fun parseInputGeneric(
        input: String,
        seaParser: (List<String>) -> Sea
    ): Triple<Sea, List<Offset>, Sea.Entity> {
        val (map, moves) = input.split(System.lineSeparator().repeat(2)).toPair().mapFirst(String::words)
        val sea = seaParser(map)
        return Triple(sea, parseMoves(moves), sea.entities.single { it.type == Submarine })
    }

    private fun parseSeaRegular(map: List<String>): Sea {
        val height = map.size
        val width = map[0].length
        val entities = map.flatMapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                val type = when (c) {
                    '#' -> Wall
                    '@' -> Submarine
                    'O' -> SmallBox
                    else -> return@mapIndexedNotNull null
                }
                Sea.Entity(type, Vec2(x, y))
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
                    '#' -> listOf(Wall(pos), Wall(pos.copy(x = pos.x + 1)))
                    '@' -> listOf(Submarine(pos))
                    'O' -> {
                        val left = BigBoxLeft(pos)
                        val right = BigBoxRight(pos.copy(x = pos.x + 1))
                        left.linked = right
                        right.linked = left
                        listOf(left, right)
                    }
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
        val next = entities.find { it.pos == targetPos }
        if (next in checked) return true
        return when (next?.type) {
            Wall -> false
            BigBoxRight -> if (canMove(next, dir) && move(next.linked, dir, checked + next)) {
                move(next, dir)
                entity.pos = targetPos
                true
            } else false

            BigBoxLeft -> if (canMove(next, dir) && move(next.linked, dir, checked + next)) {
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
        val next = entities.find { it.pos == targetPos }
        if (next != null && next in checked) return true
        return when (next?.type) {
            null -> true
            Wall -> false
            BigBoxRight -> canMove(next, dir) && canMove(next.linked, dir, checked + next)
            BigBoxLeft -> canMove(next, dir) && canMove(next.linked, dir, checked + next)
            else -> canMove(next, dir)
        }
    }

    class Entity(val type: Type, var pos: Vec2) {
        lateinit var linked: Entity
        val gps: Int get() = pos.y * 100 + pos.x

        enum class Type {
            Wall,
            Submarine,
            SmallBox,
            BigBoxLeft,
            BigBoxRight;

            operator fun invoke(pos: Vec2): Entity = Entity(this, pos)
        }
    }
}