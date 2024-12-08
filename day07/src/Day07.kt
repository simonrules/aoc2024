import java.io.File

class Day07(private val file: String) {
    enum class Operator { ADD, MULTIPLY }

    private fun doOperation(left: Long, right: Long, operator: Operator): Long {
        return when (operator) {
            Operator.ADD -> left + right
            Operator.MULTIPLY -> left * right
        }
    }

    private fun getResult(operands: List<Long>, combo: Int): Long {
        // a combination is a binary number with 0 for add and 1 for multiply
        var bit = 1
        var result = operands[0]

        for (i in 1..<operands.size) {
            val operator = if (combo and bit != 0) Operator.MULTIPLY else Operator.ADD
            result = doOperation(result, operands[i], operator)
            bit *= 2
        }

        return result
    }

    fun part1(): Long {
        var sum = 0L

        File(file).forEachLine { line ->
            val equation = line.split(": ")
            val result = equation[0].toLong()
            val operands = equation[1].split(" ").map { it.toLong() }

            val numCombos = 1.shl(operands.size - 1)
            for (combo in 0..<numCombos) {
                if (getResult(operands, combo) == result) {
                    sum += result
                    break
                }
            }
        }

        return sum
    }
}

fun main() {
    val aoc = Day07("day07/input.txt")
    println(aoc.part1())
}
