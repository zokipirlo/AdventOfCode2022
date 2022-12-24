fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val half = line.length / 2

            val p1 = line.toCharArray(0, half)
            val p2 = line.toCharArray(half)


            val equal = p1.intersect(p2.toSet())
            val offset = 'a'.code - 1
            equal.sumOf {
                when {
                    it.code >= 97 -> it.code - 96
                    it.code >= 65 -> it.code - 38
                    else -> 0
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val groups = input.chunked(3)
        return groups.flatMap { items ->
            items
                .flatMap { item -> item.toSet() }
                .groupBy { it.code }
                .filterValues { it.size == 3 }
                .keys
        }.sumOf { code ->
            when {
                code >= 97 -> code - 96
                code >= 65 -> code - 38
                else -> 0
            }
        }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
