import java.io.File
import kotlin.math.pow

class Day07(private val file: String) {
    enum class Operator { ADD, MULTIPLY, CONCAT }

    private fun doOperation(left: Long, right: Long, operator: Operator): Long {
        return when (operator) {
            Operator.ADD -> left + right
            Operator.MULTIPLY -> left * right
            Operator.CONCAT -> (left.toString() + right.toString()).toLong()
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

    private fun getResultTernary(operands: List<Long>, combo: Int): Long {
        // a combination is a ternary number with 0 for add, 1 for multiply and 2 for concat
        var c = combo
        var result = operands[0]

        for (i in 1..<operands.size) {
            val operator = when (c % 3) {
                0 -> Operator.ADD
                1 -> Operator.MULTIPLY
                2 -> Operator.CONCAT
                else -> Operator.ADD
            }
            result = doOperation(result, operands[i], operator)
            c /= 3
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

    fun part2(): Long {
        var sum = 0L

        File(file).forEachLine { line ->
            val equation = line.split(": ")
            val result = equation[0].toLong()
            val operands = equation[1].split(" ").map { it.toLong() }

            val numCombos = 3.0.pow(operands.size - 1).toInt()
            for (combo in 0..<numCombos) {
                if (getResultTernary(operands, combo) == result) {
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
    println(aoc.part2())
}
