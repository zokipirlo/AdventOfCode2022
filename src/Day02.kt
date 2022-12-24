enum class Shape(val point: Int) {
    Rock(1),
    Paper(2),
    Scissors(3)
}

data class Round(val opponent: Shape, val me: Shape) {
    fun calculateRes(): Int {
        val winPoints = when (opponent) {
            Shape.Rock -> when (me) {
                Shape.Rock -> 3
                Shape.Paper -> 6
                Shape.Scissors -> 0
            }
            Shape.Paper -> when (me) {
                Shape.Rock -> 0
                Shape.Paper -> 3
                Shape.Scissors -> 6
            }
            Shape.Scissors -> when (me) {
                Shape.Rock -> 6
                Shape.Paper -> 0
                Shape.Scissors -> 3
            }
        }
        return winPoints + me.point
    }

    companion object {
        fun parse(line: String): Round {
            val (opponent, me) = line.split(" ")
            return Round(
                opponent = when (opponent) {
                    "A" -> Shape.Rock
                    "B" -> Shape.Paper
                    "C" -> Shape.Scissors
                    else -> throw java.lang.IllegalArgumentException("Wrong item")
                },
                me = when (me) {
                    "X" -> Shape.Rock
                    "Y" -> Shape.Paper
                    "Z" -> Shape.Scissors
                    else -> throw java.lang.IllegalArgumentException("Wrong item")
                }
            )
        }

        fun parseRes(line: String): Round {
            val (opponent, me) = line.split(" ")
            val opponentShape = when (opponent) {
                "A" -> Shape.Rock
                "B" -> Shape.Paper
                "C" -> Shape.Scissors
                else -> throw java.lang.IllegalArgumentException("Wrong item")
            }
            return Round(
                opponent = opponentShape,
                me = when (me) {
                    "X" -> when (opponentShape) {
                        Shape.Rock -> Shape.Scissors
                        Shape.Paper -> Shape.Rock
                        Shape.Scissors -> Shape.Paper
                    }
                    "Y" -> opponentShape
                    "Z" -> when (opponentShape) {
                        Shape.Rock -> Shape.Paper
                        Shape.Paper -> Shape.Scissors
                        Shape.Scissors -> Shape.Rock
                    }
                    else -> throw java.lang.IllegalArgumentException("Wrong item")
                }
            )
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map {
            Round.parse(it)
        }.sumOf {
            it.calculateRes()
        }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            Round.parseRes(it)
        }.sumOf {
            it.calculateRes()
        }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
