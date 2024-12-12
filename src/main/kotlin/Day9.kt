object Day9 : AOC(9) {
    override fun part1(input: String): Long {
        val mem = parseMemory(input)
        var firstNull = mem.indexOf(-1)
        var lastNum = mem.indexOfLast { it >= 0 }
        while (firstNull < lastNum) {
            mem.swap(firstNull++, lastNum--)
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
        val (nums, spaces) = mem.indices.partition { mem[it].id != -1 }.toList().map { it.toMutableList() }
        while (nums.isNotEmpty()) {
            val numIndex = nums.removeLast()
            spaces.retainAll { it < numIndex }
            val spaceIndex = spaces.find { mem[it].size >= mem[numIndex].size } ?: continue
            val (newChunk, rest) = mem[spaceIndex].allocate(mem[numIndex].size, mem[numIndex].id)
            mem[spaceIndex] = newChunk
            mem[numIndex] = mem[numIndex].copy(id = -1)
            if (rest != null) {
                mem.add(spaceIndex + 1, rest)
                nums.replaceAll {
                    if (it >= spaceIndex) it + 1
                    else it
                }
                spaces.replaceAll {
                    if (it >= spaceIndex) it + 1
                    else it
                }
            } else {
                spaces.remove(spaceIndex)
            }
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
    val size: Int,
    val id: Int
) : Iterable<Int> {
    fun allocate(at: Int, newId: Int): Pair<Chunk, Chunk?> =
        Chunk(at, newId) to Chunk(size - at, id).takeIf { it.size > 0 }

    override fun iterator(): Iterator<Int> = iterator {
        repeat(size) { yield(id) }
    }

    override fun toString(): String =
        if (id == -1) ".".repeat(size) else id.toString().repeat(size)
}