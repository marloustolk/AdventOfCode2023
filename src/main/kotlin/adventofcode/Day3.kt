package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input

object Day3 {

    fun part1Day3(): Int = countPartNumbers()

    fun part2Day3(): Int = countGearRatios()

    private val lines = FileReader.read(3, Input.PUZZLE)

    private val lineCount = lines[0].length

    private val numberRegex = Regex("\\d+")

    private val symbolRegex = Regex("[^\\d.]+")

    private fun countPartNumbers(): Int {
        var partNumberCount = 0
        var lineNumber = 0
        while (lineNumber < lines.size) {
            val numbers = numberRegex.findAll(lines[lineNumber])

            numbers.forEach {
                if (hasASymbol(lineNumber, it)) {
                    partNumberCount += it.value.toInt()
                }
            }
            lineNumber ++
        }
        return partNumberCount
    }

    private fun countGearRatios(): Int {
        var gearRatioCount = 0
        var lineNumber = 0
        while (lineNumber < lines.size) {
            val gears = Regex("\\*").findAll(lines[lineNumber])
            gears.forEach {
                val numbersAround = getNumbersAround(lineNumber, it)
                if (numbersAround.size == 2) {
                    gearRatioCount += numbersAround[0] * numbersAround[1]
                }
            }
            lineNumber ++
        }
        return gearRatioCount
    }

    private fun getNumbersAround(lineNumber: Int, matchResult: MatchResult): List<Int> {
        return getMatchesAround(lineNumber, matchResult.range, numberRegex).map {it.toInt()}
    }

    private fun hasASymbol(lineNumber: Int, matchResult: MatchResult): Boolean {
        return getMatchesAround(lineNumber, matchResult.range, symbolRegex).isNotEmpty()
    }

    private fun getMatchesAround(lineNumber: Int, range: IntRange, regex: Regex): List<String> {
        val (top, bottom) = getLinesAboveAndBelow(lineNumber)
        val (left, right) = getIndexStartAndEnd(range)
        return (regex.findAll(top) + regex.findAll(lines[lineNumber]) + regex.findAll(bottom))
            .filter { it -> it.range.any { it in left..right } }.map {it.value}.toList()
    }

    private fun getIndexStartAndEnd(range: IntRange): Pair<Int, Int> {
        val indexStart = if (range.first - 1 < 0) 0 else range.first - 1
        val indexEnd = if (range.last + 1 > lineCount) lineCount else range.last + 1
        return indexStart to indexEnd
    }

    private fun getLinesAboveAndBelow(lineNumber: Int): Pair<String, String> {
        return getLine(lineNumber - 1) to getLine(lineNumber + 1)
    }

    private fun getLine(lineNumber: Int): String {
        return lines.getOrElse(lineNumber) { IntArray(lineCount).joinToString("") { "." } }
    }
}