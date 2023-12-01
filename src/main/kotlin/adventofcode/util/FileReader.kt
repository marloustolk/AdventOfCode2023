package adventofcode.util

import java.io.File

enum class Input(val fileName: String) {
    EXAMPLE("example-input"),
    EXAMPLE2("example-input2"),
    PUZZLE("puzzle-input")
}

object FileReader {

    fun read(day: Int, input: Input) : List<String>
        = File("src/main/resources/day$day/${input.fileName}").useLines { it.toList() }
}