//enum class Shape(val point: Int) {
//    Rock(1),
//    Paper(2),
//    Scissors(3);
//
//    fun matchPoints(other: Shape) = when {
//        this == other -> 3
//        this == Scissors && other == Rock -> 6
//        this == Rock && other == Scissors -> 0
//        this.ordinal < other.ordinal -> 6
//        else -> 0
//    }
//}
//
//data class Round(val opponent: Shape, val me: Shape) {
//    fun calculateRes(): Int {
//        val winPoints = opponent.matchPoints(me)
//        return winPoints + me.point
//    }
//    //14531
//    //11258
//
//    companion object {
//        fun parse(line: String): Round {
//            val (opponent, me) = line.split(" ")
//            return Round(
//                opponent = when (opponent) {
//                    "A" -> Shape.Rock
//                    "B" -> Shape.Paper
//                    "C" -> Shape.Scissors
//                    else -> throw java.lang.IllegalArgumentException("Wrong item")
//                },
//                me = when (me) {
//                    "X" -> Shape.Rock
//                    "Y" -> Shape.Paper
//                    "Z" -> Shape.Scissors
//                    else -> throw java.lang.IllegalArgumentException("Wrong item")
//                }
//            )
//        }
//
//        fun parseRes(line: String): Round {
//            val (opponent, me) = line.split(" ")
//            val opponentShape = when (opponent) {
//                "A" -> Shape.Rock
//                "B" -> Shape.Paper
//                "C" -> Shape.Scissors
//                else -> throw java.lang.IllegalArgumentException("Wrong item")
//            }
//            return Round(
//                opponent = opponentShape,
//                me = when (me) {
//                    "X" -> Shape.values()[if (opponentShape.ordinal - 1 == -1) 2 else opponentShape.ordinal - 1]
//                    "Y" -> opponentShape
//                    "Z" -> Shape.values()[if (opponentShape.ordinal + 1 == 3) 0 else opponentShape.ordinal + 1]
//                    else -> throw java.lang.IllegalArgumentException("Wrong item")
//                }
//            )
//        }
//    }
//}
//
//fun main() {
//    fun part1(input: List<String>): Int {
//        return input.map {
//            Round.parse(it)
//        }.sumOf {
//            it.calculateRes()
//        }
//    }
//
//    fun part2(input: List<String>): Int {
//        return input.map {
//            Round.parseRes(it)
//        }.sumOf {
//            it.calculateRes()
//        }
//    }
//
//    // test if implementation meets criteria from the description, like:
////    val testInput = readInput("Day02_test")
////    check(part1(testInput) == 1)
//
//    val input = readInput("Day02")
//    part1(input).println()
//    part2(input).println()
//}
