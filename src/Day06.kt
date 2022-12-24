fun main() {
    fun findIndex(line: String, unique: Int): Int {
        val uIndex = unique- 1
        line.forEachIndexed { index, c ->
            if (index < uIndex) {
                return@forEachIndexed
            }
            val prevChars = line.toCharArray(index - uIndex, index + 1)
            if (prevChars.distinct().size == unique) {
                return index + 1
            }
        }
        return -1
    }

    fun part1(input: List<String>): Int {
        return findIndex(input.first(), 4)
    }

    fun part2(input: List<String>): Int {
        return findIndex(input.first(), 14)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
