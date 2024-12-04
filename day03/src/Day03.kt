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

    fun part2(): Int {
        var sum = 0
        var enable = true
        val regex = "\\d+,\\d+".toRegex()
        File(file).forEachLine { line ->
            var i = 0
            while (i < line.length) {
                if ((i < line.length - 4) && (line.substring(i, i + 4) == "mul(")) {
                    i += 4
                    if (enable) {
                        // Now scan for a valid xxx,yyy pair inside the parens
                        val j = line.indexOf(')', i)
                        if (j < line.length) {
                            val pair = line.substring(i, j)
                            if (regex.matches(pair)) {
                                val numbers = pair.split(",").map { it.toInt() }
                                sum += numbers[0] * numbers[1]
                            }
                        }
                    }
                } else if ((i < line.length - 4) && (line.substring(i, i + 4) == "do()")) {
                    enable = true
                    i += 4
                } else if ((i < line.length - 7) && (line.substring(i, i + 7) == "don't()")) {
                    enable = false
                    i += 7
                } else {
                    i++
                }
            }
        }
        return sum
    }
}

fun main() {
    val aoc = Day03("day03/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
