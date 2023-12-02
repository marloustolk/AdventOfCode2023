package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input

object Day2 {

    fun part1Day2(): Int = idCountPossibleGames()

    fun part2Day2(): Int = minimumPowerCountGames()

    private val input = FileReader.read(2, Input.PUZZLE)

    private val regexColorCount = Regex("(\\d+)\\s[a-z]+")

    private fun idCountPossibleGames(): Int {
        val colorMax = mapOf("red" to 12,"green" to 13,"blue" to 14)

        return input
            .map { it ->
                val (id, colorCounts) = getColorCounts(it)
                var isValidGame = true

                colorCounts.forEach {
                    val (count, color) = it.split(" ")
                    if (colorMax[color]!! < count.toInt()) {
                        isValidGame = false
                    }
                }
                if (isValidGame) id else 0
            }
            .sumOf { it }
    }

    private fun minimumPowerCountGames(): Int {
        return input
            .map { it ->
                val colorMin = mutableMapOf("red" to 0,"green" to 0,"blue" to 0)
                val (_, colorCounts) = getColorCounts(it)

                colorCounts.forEach {
                        val (count, color) = it.split(" ")
                        if (colorMin[color]!! < count.toInt()) {
                            colorMin[color] = count.toInt()
                        }
                    }
                colorMin.values.reduce { acc, i -> acc * i }
            }
            .sumOf { it }
    }

    private fun getColorCounts(game: String): Pair<Int, List<String>> {
        val (gameId, gameInfo) = game.split(":")
        val id = gameId.split(" ")[1].toInt()
        val colorCounts = regexColorCount.findAll(gameInfo, 0).map { it.groupValues[0] }.toList()
        return id to colorCounts
    }
}