package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input

object Day5 {

    fun part1Day5(): Long = seeds.minOf { getLocation(it) }

    fun part2Day5(): Long = closestSeedLocationWithRanges()

    private val input = FileReader.read(5, Input.PUZZLE)

    private val seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }

    private val almanac = mutableListOf<List<List<Long>>>()

    private val numberRegex = Regex("\\d+")

    init {
        var lineNr = 3

        do {
            val numberList = ArrayList<List<Long>>()
            while (lineNr < input.size && input[lineNr].isNotEmpty()) {
                val numbers = numberRegex.findAll(input[lineNr]).map { it.value.toLong() }.toList()
                numberList.add(numbers)
                lineNr++
            }
            almanac.add(numberList)
            lineNr += 2
        } while (lineNr < input.size)
    }

    private fun closestSeedLocationWithRanges(): Long {
        val seedRanges = seeds.chunked(2).map { LongRange(it[0], it[0]+it[1]-1) }
        return seedRanges.minOf { getLowestLocationNumber(it) }
    }

    private fun getLowestLocationNumber(seedRange: LongRange): Long {
        var lowestLocationNr = Long.MAX_VALUE
        for (number in seedRange) {
            val newLocationNr = getLocation(number)
            lowestLocationNr = if (newLocationNr < lowestLocationNr) newLocationNr else lowestLocationNr
        }
        println("min is $lowestLocationNr for seedRange $seedRange")
        return lowestLocationNr
    }

    private fun getLocation(seed: Long): Long {
        var (source, index) = seed to 0
        do {
            source = mapToNextNumber(index, source)
            index++
        } while (index < almanac.size)
       return source
    }

    private fun mapToNextNumber(index: Int, source: Long): Long {
        for (numberList in almanac[index]){
            val (destinationStart, sourceStart, rangeLength) = numberList
            if (LongRange(sourceStart, sourceStart + rangeLength).contains(source)) {
                return source + destinationStart - sourceStart
            }
        }
        return source
    }
}