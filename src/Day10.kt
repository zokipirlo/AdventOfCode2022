sealed class SignalCommand(val cycles: Int) {
    data class Add(val value: Int) : SignalCommand(2)
    object Noop : SignalCommand(1)
}

fun main() {
    fun parseCommands(input: List<String>): List<SignalCommand> {
        return input.map {
            val parts = it.split(" ")
            when (parts[0]) {
                "addx" -> SignalCommand.Add(parts[1].toInt())
                "noop" -> SignalCommand.Noop
                else -> throw IllegalArgumentException("Unknown command")
            }
        }
    }

    fun part1(input: List<String>): Int {
        var x = 1
        var strengthCycle = 0
        var nextStrengthCycle = 20
        val signalStrengths = mutableListOf<Int>()
        var cycles = 1
        parseCommands(input).forEach { command ->
            val newX = when (command) {
                is SignalCommand.Add -> x + command.value
                SignalCommand.Noop -> x
            }
            cycles += command.cycles
            when (cycles) {
                nextStrengthCycle -> {
                    signalStrengths.add(newX * nextStrengthCycle)
                    strengthCycle++
                    nextStrengthCycle = strengthCycle * 40 + 20
                }
                nextStrengthCycle + 1 -> {
                    signalStrengths.add(x * nextStrengthCycle)
                    strengthCycle++
                    nextStrengthCycle = strengthCycle * 40 + 20
                }
            }
            x = newX
        }
        println(signalStrengths)
        return signalStrengths.sum()
    }

    fun part2(input: List<String>): String {
        var x = 1
        var cycles = 1
        val lineBuilder = StringBuilder()
        parseCommands(input).forEach { command ->
            val newX = when (command) {
                is SignalCommand.Add -> x + command.value
                SignalCommand.Noop -> x
            }
            repeat(command.cycles) {
                val linePos = cycles % 40
                if (linePos in (x ..x + 2)) {
                    lineBuilder.append("#")
                } else {
                    lineBuilder.append(".")
                }
                cycles++
                if (linePos == 0) {
                    lineBuilder.append("\n")
                }
            }
            x = newX

        }
        return lineBuilder.toString()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)

    val input = readInput("Day10")
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
