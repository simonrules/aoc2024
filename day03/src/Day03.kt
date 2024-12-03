import java.io.File

class Day03(private val file: String) {
    fun part1(): Int {
        val regex = "mul\\(\\d+,\\d+\\)".toRegex()
        var sum = 0
        File(file).forEachLine { line ->
            val matches = regex.findAll(line).map { it.value }
            matches.forEach { match->
                // Strip mul( and )
                val pair = match.drop(4).dropLast(1)
                val numbers = pair.split(",").map { it.toInt() }
                sum += numbers[0] * numbers[1]
            }
        }
        return sum
    }
}

fun main() {
    val aoc = Day03("day03/input.txt")
    println(aoc.part1())
}
