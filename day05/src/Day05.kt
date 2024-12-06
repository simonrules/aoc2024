import java.io.File

class Day05(private val file: String) {
    private fun isBefore(ordering: List<Pair<Int, Int>>, before: Int, value: Int): Boolean {
        val after = ordering.filter { it.first == before }.map { it.second }

        // If there are no rules, just return true
        if (after.isEmpty()) {
            return true
        }

        return after.contains(value)
    }
    private fun isAfter(ordering: List<Pair<Int, Int>>, value: Int, after: Int): Boolean {
        val before = ordering.filter { it.second == after }.map { it.first }

        // If there are no rules, just return true
        if (before.isEmpty()) {
            return true
        }

        return before.contains(value)
    }

    private fun isValidUpdate(ordering: List<Pair<Int, Int>>, values: List<Int>): Boolean {
        for (i in 0..<values.size - 1) {
            if (!isBefore(ordering, values[i], values[i + 1]) || !isAfter(ordering, values[i], values[i + 1])) {
                return false
            }
        }
        return true
    }

    fun part1(): Int {
        var sum = 0

        val sections = File(file).readText().trim().split("\n\n")
        val first = sections[0].split("\n")
        val ordering = first.map {
            val parts = it.split("|")
            Pair(parts[0].toInt(), parts[1].toInt())
        }
        val second = sections[1].split("\n")
        second.forEach {
            val values = it.split(",").map { it.toInt() }
            if (isValidUpdate(ordering, values)) {
                sum += values[values.size / 2]
            }
        }

        return sum
    }
}

fun main() {
    val aoc = Day05("day05/input.txt")
    println(aoc.part1())
}
