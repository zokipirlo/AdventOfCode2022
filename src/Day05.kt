import java.util.*

data class Cargo(val stacks: List<ArrayDeque<Char>>) {
    companion object {
        fun parse(lines: List<String>, stackCount: Int): Cargo {
            val stacks = List(stackCount) { ArrayDeque<Char>() }
            lines.asReversed().forEach { line ->
                val cols = line.chunked(4) { it.trim() }
                cols.forEachIndexed { index, s ->
                    if (s.length == 3) {
                        stacks[index].push(s[1])
                    }
                }
            }
            return Cargo(stacks)
        }
    }
}

data class CargoCommand(val move: Int, val from: Int, val to: Int) {
    companion object {
        fun parse(line: String): CargoCommand {
            val parsed = line.split(" ")
            return CargoCommand(
                parsed[1].trim().toInt(),
                parsed[3].trim().toInt() - 1,
                parsed[5].trim().toInt() - 1
            )
        }
    }
}

fun main() {
    fun part1(input: List<String>): String {
        val cargo = Cargo.parse(input.subList(0, 8), 9)
        input.listIterator(10).forEach {
            val command = CargoCommand.parse(it)
            repeat(command.move) {
                cargo.stacks[command.to].push(
                    cargo.stacks[command.from].pop()
                )
            }
        }
        return cargo.stacks.map { it.first }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val cargo = Cargo.parse(input.subList(0, 8), 9)
        input.listIterator(10).forEach {
            val command = CargoCommand.parse(it)
            val items = (0 until command.move).map {
                cargo.stacks[command.from].pop()
            }
            items.asReversed().forEach {
                cargo.stacks[command.to].push(it)
            }
        }
        return cargo.stacks.map { it.first }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
