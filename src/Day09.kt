import kotlin.math.abs

data class RopePos(val x: Int, val y: Int) {
    fun touching(pos: RopePos): Boolean = abs(x - pos.x) <= 1 && abs(y - pos.y) <= 1

    fun move(head: RopePos): RopePos {
        val x = when (head.x - x) {
            2, 1 -> x + 1
            -2, -1 -> x - 1
            else -> x
        }
        val y = when (head.y - y) {
            2, 1 -> y + 1
            -2, -1 -> y - 1
            else -> y
        }
        return RopePos(x, y)
    }
}

enum class RopeDirection {
    Left, Right, Up, Down
}
data class RopeCommand(val direction: RopeDirection, val moves: Int)

fun main() {
    fun parseCommands(input: List<String>): List<RopeCommand> {
        return input.map {
            val (dir, mov) = it.split(" ")
            when (dir) {
                "R" -> RopeCommand(RopeDirection.Right, mov.toInt())
                "U" -> RopeCommand(RopeDirection.Up, mov.toInt())
                "D" -> RopeCommand(RopeDirection.Down, mov.toInt())
                "L" -> RopeCommand(RopeDirection.Left, mov.toInt())
                else -> throw IllegalArgumentException("Unknown command")
            }
        }
    }

    fun part1(input: List<String>): Int {
        var head = RopePos(0, 0)
        var tail = RopePos(0, 0)
        val tailPositions = mutableSetOf(tail)

        parseCommands(input).forEach { command ->
            repeat(command.moves) {
                when (command.direction) {
                    RopeDirection.Left -> head = head.copy(x = head.x - 1)
                    RopeDirection.Right -> head = head.copy(x = head.x + 1)
                    RopeDirection.Up -> head = head.copy(y = head.y + 1)
                    RopeDirection.Down -> head = head.copy(y = head.y - 1)
                }
                if (!tail.touching(head)) {
                    tail = tail.move(head)
                    tailPositions.add(tail)
                }
//                println("head=$head, tail=$tail")
            }
        }

//        println("tailPositions ${tailPositions.size}")
        return tailPositions.size
    }

    fun part2(input: List<String>): Int {
        val knots = MutableList(10) {
            RopePos(0, 0)
        }
        val tailPositions = mutableSetOf(knots.last())

        parseCommands(input).forEach { command ->
            repeat(command.moves) {

                knots.forEachIndexed { index, knot ->
                    if (index == 0) {
                        val updatedHead = when (command.direction) {
                            RopeDirection.Left -> knot.copy(x = knot.x - 1)
                            RopeDirection.Right -> knot.copy(x = knot.x + 1)
                            RopeDirection.Up -> knot.copy(y = knot.y + 1)
                            RopeDirection.Down -> knot.copy(y = knot.y - 1)
                        }
                        knots[index] = updatedHead
                    } else {
                        val prevKnot = knots[index - 1]
                        if (!knot.touching(prevKnot)) {
                            val newPos = knot.move(prevKnot)
                            knots[index] = newPos
                        }
                    }
                }
                tailPositions.add(knots.last())

//                println("knots=$knots")
            }
        }

//        println("tailPositions ${tailPositions.size}")
        return tailPositions.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
