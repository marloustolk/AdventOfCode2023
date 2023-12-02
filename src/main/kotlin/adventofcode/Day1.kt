package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input

object Day1 {

    fun part1Day1(): Int = findCalibrations(input)

    fun part2Day1(): Int = findCalibrations(input.map { addNumbers(it) })

    private val input = FileReader.read(1, Input.PUZZLE)

    private fun findCalibrations(lines: List<String>): Int {
        return lines
            .map { it -> it.filter { it.isDigit() } }
            .map { if (it.length != 2) "${it[0]}${it[it.length - 1]}" else it }
            .sumOf { it.toInt() }
    }

    private val numbers = mapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven", 8 to "eight", 9 to "nine")

    private fun addNumbers(line: String): String {
        var newLine = ""
        line.forEach {
            newLine += it
            numbers.forEach { number -> newLine = newLine.replace(number.value, "${number.key}${number.value}") }
        }
        return newLine
    }
}