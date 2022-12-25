data class Monkey(
    val items: MutableList<Long>,
    val operation: Operation,
    val testDivisible: Long,
    val testTrueMonkey: Int,
    val testFalseMonkey: Int,
    var inspects: Long = 0
) {
    sealed class Operation {
        data class Plus(val value: Int) : Operation()
        data class Multiply(val value: Int) : Operation()
        object PlusSelf : Operation()
        object MultiplySelf : Operation()
    }
}

fun main() {
    fun parse(input: List<String>): List<Monkey> {
        return input.chunked(7).mapIndexed { index, monkeyLines ->
            val items = monkeyLines[1].trim().removePrefix("Starting items: ").split(", ")
            val operationLine = monkeyLines[2].trim().removePrefix("Operation: new = old ").split(" ")
            Monkey(
                items = items.map { it.toLong() }.toMutableList(),
                operation = when (operationLine[0]) {
                    "*" -> when (operationLine[1]) {
                        "old" -> Monkey.Operation.MultiplySelf
                        else -> Monkey.Operation.Multiply(operationLine[1].toInt())
                    }
                    "+" -> when (operationLine[1]) {
                        "old" -> Monkey.Operation.PlusSelf
                        else -> Monkey.Operation.Plus(operationLine[1].toInt())
                    }
                    else -> throw IllegalArgumentException("unknown operation")
                },
                testDivisible = monkeyLines[3].trim().removePrefix("Test: divisible by ").toLong(),
                testTrueMonkey = monkeyLines[4].trim().removePrefix("If true: throw to monkey ").toInt(),
                testFalseMonkey = monkeyLines[5].trim().removePrefix("If false: throw to monkey ").toInt(),
            )
        }
    }

    fun part1(input: List<String>): Long {
        val monkeys = parse(input)
        repeat(20) {
            monkeys.forEach { monkey ->
                monkey.inspects += monkey.items.size
                monkey.items.forEach {  level ->
                    val newLevel = when (monkey.operation) {
                        is Monkey.Operation.Multiply -> level * monkey.operation.value
                        Monkey.Operation.MultiplySelf -> level * level
                        is Monkey.Operation.Plus -> level + monkey.operation.value
                        Monkey.Operation.PlusSelf -> level + level
                    }
                    val divisible = newLevel / 3
                    if (divisible.rem(monkey.testDivisible) == 0L) {
                        monkeys[monkey.testTrueMonkey].items.add(divisible)
                    } else {
                        monkeys[monkey.testFalseMonkey].items.add(divisible)
                    }
                }
                monkey.items.clear()
            }
        }
        return monkeys.map { it.inspects }.sortedDescending().take(2).reduce(Long::times)
    }

    fun part2(input: List<String>): Long {
        val monkeys = parse(input)
        val divProd = monkeys.map { it.testDivisible }.reduce(Long::times)
        repeat(10_000) {
            monkeys.forEach { monkey ->
                monkey.inspects += monkey.items.size
                monkey.items.forEach {  level ->
                    val newLevel = when (monkey.operation) {
                        is Monkey.Operation.Multiply -> level * monkey.operation.value
                        Monkey.Operation.MultiplySelf -> level * level
                        is Monkey.Operation.Plus -> level + monkey.operation.value
                        Monkey.Operation.PlusSelf -> level + level
                    }
                    val divisible = newLevel % divProd
                    if (divisible.rem(monkey.testDivisible) == 0L) {
                        monkeys[monkey.testTrueMonkey].items.add(divisible)
                    } else {
                        monkeys[monkey.testFalseMonkey].items.add(divisible)
                    }
                }
                monkey.items.clear()
            }
        }
        return monkeys.map { it.inspects }.sortedDescending().take(2).reduce(Long::times)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readInput("Day11")
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
