data class SimpleFile(val name: String, val size: Int)
data class SimpleDir(
    val name: String,
    val parent: ParentDir,
    val dirs: MutableList<SimpleDir> = mutableListOf(),
    val files: MutableList<SimpleFile> = mutableListOf(),
    var size: Int = 0
)

sealed class ParentDir {
    data class Dir(val dir: SimpleDir) : ParentDir()
    object Root : ParentDir()
}

data class SimpleFs(
    val rootDir: SimpleDir
)

fun getOrCreateDir(currentDir: SimpleDir, folderName: String): SimpleDir {
    return currentDir.dirs.find { it.name == folderName } ?: run {
        val dir = SimpleDir(folderName, ParentDir.Dir(currentDir))
        currentDir.dirs.add(dir)
        dir
    }
}

fun getOrCreateFile(currentDir: SimpleDir, name: String, size: Int): SimpleFile {
    return currentDir.files.find { it.name == name } ?: run {
        val file = SimpleFile(name, size)
        currentDir.files.add(file)
        file
    }
}

fun buildFs(input: List<String>): SimpleFs {
    val fs = SimpleFs(SimpleDir("/", ParentDir.Root))
    var currentDir = fs.rootDir
    input.forEach { command ->
        when {
            command.startsWith("$") -> {
                val simpleCommand = command.substring(2)
//                println("command $simpleCommand")
                when {
                    simpleCommand.startsWith("cd") -> {
                        val folderName = simpleCommand.split(" ")[1]
//                        println("cd $folderName")
                        when (folderName) {
                            "/" -> currentDir = fs.rootDir
                            ".." -> {
                                val parent = currentDir.parent
                                if (parent is ParentDir.Dir) {
                                    currentDir = parent.dir
                                }
                            }
                            else -> {
                                val newDir = getOrCreateDir(currentDir, folderName)
                                currentDir = newDir
                            }
                        }
                    }
                    simpleCommand == "ls" -> {
//                        println("ls")
                    }
                }
            }
            command.startsWith("dir") -> {
                val folderName = command.split(" ")[1]
//                println("dir $folderName")
                getOrCreateDir(currentDir, folderName)
            }
            else -> {
                val (size, name) = command.split(" ")
//                println("file $name $size")
                getOrCreateFile(currentDir, name, size.toInt())
            }
        }
    }
    return fs
}

fun calculateDirSize(dir: SimpleDir) {
    dir.dirs.forEach {
        calculateDirSize(it)
        dir.size += it.size
    }
    dir.files.forEach {
        dir.size += it.size
    }

}
fun calculateSize(fs: SimpleFs) {
    calculateDirSize(fs.rootDir)
}

fun main() {
    fun getMaxDirSizes(dir: SimpleDir, maxDirSize: Int, elements: MutableList<Int>) {
        if (dir.size <= maxDirSize) {
            elements.add(dir.size)
        }
        dir.dirs.forEach {
            getMaxDirSizes(it, maxDirSize,  elements)
        }
    }

    fun getMinDirSizes(dir: SimpleDir, minDirSize: Int, elements: MutableList<Int>) {
        if (dir.size > minDirSize) {
            elements.add(dir.size)
        }
        dir.dirs.forEach {
            getMinDirSizes(it, minDirSize,  elements)
        }
    }

    fun part1(input: List<String>): Int {
        val fs = buildFs(input)
        calculateSize(fs)
        val allSizes = mutableListOf<Int>()
        getMaxDirSizes(fs.rootDir, 100000, allSizes)
        return allSizes.sum()
    }

    fun part2(input: List<String>): Int {
        val fs = buildFs(input)
        calculateSize(fs)
        val totalSize = 70000000
        val free = totalSize - fs.rootDir.size
        val missing = 30000000 - free
//        println("total $totalSize, free $free, missing: $missing")
        val allSizes = mutableListOf<Int>()
        getMinDirSizes(fs.rootDir, missing, allSizes)
        return allSizes.min()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
