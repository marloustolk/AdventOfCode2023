package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input
import java.util.*

object Day4 {

    fun part1Day4(): Int = countPoints()

    fun part2Day4(): Int = processScratchcards()

    private val scratchcards = FileReader.read(4, Input.PUZZLE)

    private var winningNumberCount = scratchcards.map { countWinningNumbers(it) }.toList()

    private fun processScratchcards(): Int {
        var cardCount = scratchcards.size
        var newCards = listOf(IntRange(0, cardCount - 1))
        do {
            newCards = getNewCards(newCards)
            cardCount += newCards.flatten().size
        } while (newCards.isNotEmpty())
        return cardCount
    }
    private fun getNewCards(cards: List<IntRange>): List<IntRange> {
        return cards.asSequence().flatten()
            .filter { it < scratchcards.size }
            .map { it to winningNumberCount[it]}
            .filter { (_, score) -> score > 0}
            .map { (index, score) -> IntRange(index + 1, index + score)
            }.toList()
    }
    private fun countPoints(): Int {
        return scratchcards.sumOf {
            val winningNumbers = countWinningNumbers(it)
            if (winningNumbers > 0) IntArray(winningNumbers) { 1 }.reduce { acc, _ -> acc * 2 } else 0
        }
    }

    private fun countWinningNumbers(scratchcard: String): Int {
        return getAllNumbers(scratchcard).groupingBy { it }.eachCount().filter { it.value > 1 }.size
    }

    private fun getAllNumbers(scratchcard: String): List<String> {
        return Regex("\\d+").findAll(scratchcard.split(":")[1]).map { it.value }.toList()
    }
}