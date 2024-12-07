import java.io.File

class Day05(file: String) {
    private val ordering: List<Pair<Int, Int>>
    private val updates = mutableListOf<List<Int>>()
    private val incorrect = mutableListOf<List<Int>>()

    init {
        val sections = File(file).readText().trim().split("\n\n")
        val first = sections[0].split("\n")
        ordering = first.map {
            val parts = it.split("|")
            Pair(parts[0].toInt(), parts[1].toInt())
        }
        val second = sections[1].split("\n")
        second.forEachIndexed { index, s ->
            val values = s.split(",").map { it.toInt() }
            updates.add(values)
        }
    }

    private fun isBefore(before: Int, value: Int): Boolean? {
        val after = ordering.filter { it.first == before }.map { it.second }

        // If there are no rules, return null
        if (after.isEmpty()) {
            return null
        }

        return after.contains(value)
    }
    private fun isAfter(value: Int, after: Int): Boolean? {
        val before = ordering.filter { it.second == after }.map { it.first }

        // If there are no rules, return null
        if (before.isEmpty()) {
            return null
        }

        return before.contains(value)
    }

    private fun isValidUpdate(values: List<Int>): Boolean {
        for (i in 0..<values.size - 1) {
            if (isBefore(values[i], values[i + 1]) == false || isAfter(values[i], values[i + 1]) == false) {
                return false
            }
        }
        return true
    }

    fun part1(): Int {
        var sum = 0

        updates.forEach { values ->
            if (isValidUpdate(values)) {
                sum += values[values.size / 2]
            } else {
                incorrect.add(values)
            }
        }

        return sum
    }

    private fun findInsertionIndex(list: List<Int>, value: Int): Int {
        for (i in list.indices) {
            // It should be enough to check isBefore if the rules are complete
            if (isBefore(value, list[i]) == true) {
                return i
            }
        }

        return list.size
    }

    fun part2(): Int {
        var sum = 0
        for (unordered in incorrect) {
            val ordered = mutableListOf<Int>()
            for (value in unordered) {
                if (ordered.isEmpty()) {
                    ordered.add(value)
                } else {
                    // Find the right place for the new value in the ordered list
                    val index = findInsertionIndex(ordered, value)
                    ordered.add(index, value)
                }
            }

            sum += ordered[ordered.size / 2]
        }
        return sum
    }
}

fun main() {
    val aoc = Day05("day05/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
