import java.io.File
import kotlin.math.absoluteValue

class Day02(private val file: String) {
    private fun isReportValid(levels: List<Int>): Boolean {
        val deltas = levels.zipWithNext { a, b -> b - a }
        var isSafe = true
        val isPositive = deltas[0] > 0
        deltas.forEach {
            if ((it > 0 != isPositive) || (it.absoluteValue > 3) || (it.absoluteValue == 0)) {
                isSafe = false
            }
        }
        return isSafe
    }

    private fun isModifiedReportValid(levels: List<Int>): Boolean {
        if (isReportValid(levels)) {
            return true
        }

        // Remove one element at each index to generate a new levels list
        for (i in levels.indices) {
            val newLevels = levels.toMutableList().apply {
                removeAt(i)
            }
            if (isReportValid(newLevels)) {
                return true
            }
        }

        return false
    }

    fun part1(): Int {
        var count = 0
        File(file).forEachLine { report ->
            val levels = report.split(" ").map { it.toInt() }
            if (isReportValid(levels)) {
                count++
            }
        }
        return count
    }

    fun part2(): Int {
        var count = 0
        File(file).forEachLine { report ->
            val levels = report.split(" ").map { it.toInt() }
            if (isModifiedReportValid(levels)) {
                count++
            }
        }
        return count
    }
}

fun main() {
    val aoc = Day02("day02/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
