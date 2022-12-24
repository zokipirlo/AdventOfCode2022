data class Section(val elf1: IntRange, val elf2: IntRange) {
    fun rangeFullyOverlap(): Boolean {
        return when {
            elf1.first <= elf2.first && elf1.last >= elf2.last -> true
            elf2.first <= elf1.first && elf2.last >= elf1.last -> true
            else -> false
        }
    }

    fun rangeOverlap(): Boolean {
        return when {
            elf1.last < elf2.first -> false
            elf1.first > elf2.last -> false
            else -> true
        }
    }

    companion object {
        private fun getRange(range: String): IntRange {
            val (r1, r2) = range.split("-")
            return IntRange(r1.toInt(), r2.toInt())
        }

        fun parse(line: String): Section {
            val (e1, e2) = line.split(",")
            return Section(
                elf1 = getRange(e1),
                elf2 = getRange(e2),
            )
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { Section.parse(it) }
            .count { it.rangeFullyOverlap() }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { Section.parse(it) }
            .count { it.rangeOverlap() }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
