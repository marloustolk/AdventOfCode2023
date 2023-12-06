package adventofcode

import adventofcode.util.FileReader
import adventofcode.util.Input

object Day6 {

    fun part1Day6(): Int = powerOfWaysToBeatTheRecord()

    fun part2Day6(): Int = waysToBeatOneRecord()

    private val input = FileReader.read(6, Input.PUZZLE)

    private val timeToRecordList = mutableListOf<Pair<Long, Long>>()

    private val numberRegex = Regex("\\d+")

    init {
        val times = numberRegex.findAll(input[0]).map{it.value.toLong()}.toList()
        val records = numberRegex.findAll(input[1]).map{it.value.toLong()}.toList()
        IntRange(0, times.size -1).forEach { timeToRecordList.add(times[it] to records[it]) }
    }

    private fun powerOfWaysToBeatTheRecord(): Int {
        return timeToRecordList.map{ waysToBeatRecord(it) }.reduce{acc, i -> acc * i}
    }

    private fun waysToBeatOneRecord(): Int {
        val time = timeToRecordList.joinToString("") { it.first.toString() }.toLong()
        val record = timeToRecordList.joinToString("") { it.second.toString() }.toLong()
        return waysToBeatRecord(time to record)
    }

    private fun waysToBeatRecord(record: Pair<Long, Long>): Int {
        return LongRange(0, record.first).map { nr -> nr * (record.first - nr) }.count { time -> time > record.second }
    }

}