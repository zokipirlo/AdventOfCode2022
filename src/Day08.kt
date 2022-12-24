typealias Grid = List<List<Int>>
fun main() {
    fun parseGrid(input: List<String>): Grid {
        return input.map { line ->
            line.map { it.digitToInt() }
        }
    }

    fun scanLeft(line: List<Int>, item: Int, index: Int): Boolean {
        if (index == 0) {
            return false
        }

        val it = line.listIterator(index)
        while (it.hasPrevious()) {
            if (it.previous() >= item) {
                return true
            }
        }
        return false
    }

    fun scanRight(line: List<Int>, item: Int, index: Int): Boolean {
        if (index == line.lastIndex) {
            return false
        }
        line.listIterator(index + 1).forEach {
            if (it >= item) {
                return true
            }
        }
        return false
    }

    fun scanUp(grid: Grid, rowIndex: Int, colIndex: Int, item: Int): Boolean {
        if (rowIndex == 0) {
            return false
        }

        val it = grid.listIterator(rowIndex)
        while (it.hasPrevious()) {
            val prevLine = it.previous()
            if (prevLine[colIndex] >= item) {
                return true
            }
        }
        return false
    }

    fun scanDown(grid: Grid, rowIndex: Int, colIndex: Int, item: Int): Boolean {
        if (rowIndex == grid.lastIndex) {
            return false
        }
        grid.listIterator(rowIndex + 1).forEach {
            if (it[colIndex] >= item) {
                return true
            }
        }
        return false
    }

    fun countLeft(line: List<Int>, item: Int, index: Int): Int {
        if (index == 0) {
            return 0
        }

        var iterations = 0
        val it = line.listIterator(index)
        while (it.hasPrevious()) {
            iterations++
            if (it.previous() >= item) {
                return iterations
            }
        }
        return iterations
    }

    fun countRight(line: List<Int>, item: Int, index: Int): Int {
        if (index == line.lastIndex) {
            return 0
        }
        var iterations = 0
        line.listIterator(index + 1).forEach {
            iterations++
            if (it >= item) {
                return iterations
            }
        }
        return iterations
    }

    fun countUp(grid: Grid, rowIndex: Int, colIndex: Int, item: Int): Int {
        if (rowIndex == 0) {
            return 0
        }

        var iterations = 0
        val it = grid.listIterator(rowIndex)
        while (it.hasPrevious()) {
            iterations++
            val prevLine = it.previous()
            if (prevLine[colIndex] >= item) {
                return iterations
            }
        }
        return iterations
    }

    fun countDown(grid: Grid, rowIndex: Int, colIndex: Int, item: Int): Int {
        if (rowIndex == grid.lastIndex) {
            return 0
        }
        var iterations = 0
        grid.listIterator(rowIndex + 1).forEach {
            iterations++
            if (it[colIndex] >= item) {
                return iterations
            }
        }
        return iterations
    }

    fun part1(input: List<String>): Int {
        val grid = parseGrid(input)
        var visible = 0
        grid.forEachIndexed row@ { rowIndex, row ->
            row.forEachIndexed col@ { colIndex, col ->
                if (!scanLeft(row, col, colIndex)) {
//                    println("scanLeft $col [$rowIndex, $colIndex]")
                    visible++
                    return@col
                }
                if (!scanRight(row, col, colIndex)) {
//                    println("scanRight $col [$rowIndex, $colIndex]")
                    visible++
                    return@col
                }
                if (!scanUp(grid, rowIndex, colIndex, col)) {
//                    println("scanUp $col [$rowIndex, $colIndex]")
                    visible++
                    return@col
                }
                if (!scanDown(grid, rowIndex, colIndex, col)) {
//                    println("scanDown $col [$rowIndex, $colIndex]")
                    visible++
                    return@col
                }
            }
        }
        return visible
    }

    fun part2(input: List<String>): Int {
        val grid = parseGrid(input)
        var max = 0
        grid.forEachIndexed row@ { rowIndex, row ->
            row.forEachIndexed col@ { colIndex, col ->
                val left = countLeft(row, col, colIndex)
                val right = countRight(row, col, colIndex)
                val up = countUp(grid, rowIndex, colIndex, col)
                val down = countDown(grid, rowIndex, colIndex, col)
                val score = left * right * up * down
//                println("$left $right $up $down:  $col [$rowIndex, $colIndex]")
                if (score > max){
                    max = score
                }
            }
        }
//        println("max $max")
        return max
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
