import java.io.File
import kotlin.math.absoluteValue

class Day01(file: String) {
    private val listA = mutableListOf<Int>()
    private val listB = mutableListOf<Int>()

    init {
        File(file).forEachLine {
            val row = it.split("   ")
            listA.add(row[0].toInt())
            listB.add(row[1].toInt())
        }

        listA.sort()
        listB.sort()
    }

    fun part1(): Int {
        var sum = 0
        listA.forEachIndexed { index, a ->
            sum += (a - listB[index]).absoluteValue
        }
        return sum
    }

    fun part2(): Int {
        var similarity = 0
        listA.forEach { a ->
            similarity += a * listB.count { it == a }
        }
        return similarity
    }
}

fun main() {
    val aoc = Day01("day01/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
