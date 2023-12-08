package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input

object Day7 {

    fun part1Day7(): Int = multiplyBidsWithRank()

    fun part2Day7(): Int = multiplyBidsWithRankWithJokers()

    private val input = FileReader.read(7, Input.PUZZLE)

    private val findSetsOfCards = { s: String -> "(.)\\1+".toRegex().findAll(s).map{it.value}.toList()}

    private val splitToHandAndBid = {s: String -> s.split(" ").zipWithNext()[0]}

    private val sortCardsInHand = {s: String -> s.toCharArray().sorted().joinToString("")}

    private enum class Strength(val value: Int)
    { HIGH_CARD(0), ONE_PAIR(1), TWO_PAIR(2), THREE_OF_A_KIND(3), FULL_HOUSE(4), FOUR_OF_A_KIND(5), FIVE_OF_A_KIND(6)}

    private fun multiplyBidsWithRank(): Int {
        return input.map { splitToHandAndBid(it)}
            .sortedWith(compareBy({ scoreStrength(it.first)}, {toComparableString(it.first)}))
            .mapIndexed {index, pair -> (index + 1) * pair.second.toInt() }
            .sum()
    }

    private fun multiplyBidsWithRankWithJokers(): Int {
        return input.map { splitToHandAndBid(it)}
            .sortedWith(compareBy({ scoreStrength(it.first, true)}, {toComparableString(it.first, true)}))
            .mapIndexed {index, pair -> (index + 1) * pair.second.toInt() }
            .sum()
    }

    private fun toComparableString(hand: String, withJokers: Boolean = false): String {
        return hand.replace("T", "a")
            .replace("J", if (withJokers) "1" else "b")
            .replace("Q", "c")
            .replace("K", "d")
            .replace("A", "e")
    }

    private fun scoreStrength(hand: String, withJokers: Boolean = false): Int {
        val sortedCards = sortCardsInHand(hand)
        val setsOfCards = if (withJokers) addJokersToSets(sortedCards) else findSetsOfCards(sortedCards)

        if (setsOfCards.isEmpty()) return Strength.HIGH_CARD.value
        if (setsOfCards.size == 1) {
            val set = setsOfCards[0]
            if (set.length == 2) return Strength.ONE_PAIR.value
            if (set.length == 3) return Strength.THREE_OF_A_KIND.value
            if (set.length == 4) return Strength.FOUR_OF_A_KIND.value
            return Strength.FIVE_OF_A_KIND.value
        }
        if (setsOfCards.all { it.length == 2 }) return Strength.TWO_PAIR.value
        return Strength.FULL_HOUSE.value
    }

    private fun addJokersToSets(hand: String): List<String> {
        val jokerIndexes = hand.mapIndexed { index, card -> if (card == "J".toCharArray()[0]) index else - 1 }.filter { it != -1 }
        if (jokerIndexes.isEmpty()) return findSetsOfCards(hand)

        val foundSets = findSetsOfCards(hand).filter { !it.contains("J") }
        val newCard =  if (foundSets.isEmpty()) getCardForJokerReplacement(hand, jokerIndexes) else foundSets[0][0].toString()

        val newHand = sortCardsInHand(hand.replace("J", newCard))
        return findSetsOfCards(newHand)
    }

    private fun getCardForJokerReplacement(hand: String, jokerIndexes: List<Int>): String {
        val index: Int? = IntRange(0, 4).firstOrNull { !jokerIndexes.contains(it) }
        return if (index != null) hand[index].toString() else "a"
    }

}