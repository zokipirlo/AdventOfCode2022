fun main() {
    fun getSums(input: List<String>): List<Int> {
        val sums = mutableListOf<Int>()
        var sum = 0
        input.forEach {
            if (it.isEmpty()) {
                sums.add(sum)
                sum = 0
            } else {
                sum += it.toInt()
            }
        }
        if (sum != 0) {
            sums.add(sum)
        }
        return sums
    }
    fun part1(input: List<String>): Int {
        return getSums(input).max()
    }

    fun part2(input: List<String>): Int {
        return getSums(input).sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
