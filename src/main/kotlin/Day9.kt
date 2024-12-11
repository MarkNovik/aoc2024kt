object Day9 : AOC(9) {
    override fun part1(input: String): Long {
        val mem = parseMemory(input)
        var firstNull = mem.indexOf(-1)
        var lastNum = mem.indexOfLast { it >= 0 }
        while (firstNull < lastNum) {
            mem[firstNull++] = mem[lastNum]
            mem[lastNum--] = -1
            while (mem[firstNull] != -1) firstNull++
            while (mem[lastNum] == -1) lastNum--
        }
        return mem
            .takeWhile { it >= 0 }
            .foldIndexed(0L) { i, acc, next ->
                acc + (i * (next))
            }
    }

    override fun part2(input: String): Long {
        val mem = parseChunks(input).toMutableList()
        var numIndex = mem.indexOfLast { it.id != -1 }
        var id = mem[numIndex].id
        out@ while (id > -1) {
            val nullIndex = mem.take(numIndex).indexOfFirst { it.id == -1 && it.size >= mem[numIndex].size }
            if (nullIndex == -1) {
                id--
                while (mem[numIndex].id != id) if (--numIndex < 0) continue@out
                continue
            }
            val rest = mem[nullIndex].split(mem[numIndex].size, id)
            mem[numIndex].id = -1
            mem.add(nullIndex + 1, rest)
            id--
            while (mem[numIndex].id != id) if (--numIndex < 0) break@out
        }
        return mem.flatten().foldIndexed(0L) { index: Int, acc: Long, next: Int ->
            acc + (index * next.coerceAtLeast(0))
        }
    }

    private fun parseChunks(input: String): List<Chunk> {
        var id = 0
        var mode = true
        return buildList {
            for (size in input.map(Char::digitToInt)) {
                if (size != 0) add(
                    if (mode) Chunk(size, id++) else Chunk(size, -1)
                )
                mode = !mode
            }
        }
    }

    private fun parseMemory(input: String): IntArray =
        input.asSequence().map(Char::digitToInt).zip(
            generateSequence(0, Int::inc)
        ) { size, id ->
            when (id % 2 == 0) {
                true -> List(size) { id / 2 }
                false -> List(size) { -1 }
            }
        }.flatten().toList().toIntArray()
}

private data class Chunk(
    var size: Int,
    var id: Int
) : Iterable<Int> {
    fun split(at: Int, newId: Int): Chunk {
        val res = Chunk(size - at, id)
        size = at
        id = newId
        return res
    }

    override fun iterator(): Iterator<Int> = iterator {
        repeat(size) { yield(id) }
    }
}