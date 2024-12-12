import java.io.File

class Day11(file: String) {
    private val list = File(file).readLines().first().split(" ").map { it.toLong() }

    private fun blink(list: List<Long>): MutableList<Long> {
        val transformed = mutableListOf<Long>()
        for (element in list) {
            val str = element.toString()
            val len = str.length
            if (element == 0L) {
                transformed.add(1L)
            } else if (len % 2L == 0L) {
                transformed.add(str.substring(0, len / 2).toLong())
                transformed.add(str.substring(len / 2).toLong())
            } else {
                transformed.add(element * 2024L)
            }
        }
        return transformed
    }

    fun part1(): Int {
        var blinked = list.toMutableList()
        repeat(25) {
            blinked = blink(blinked)
        }
        return blinked.size
    }

    private fun blinkMap(frequency: Map<Long, Long>): MutableMap<Long, Long> {
        val blinked = mutableMapOf<Long, Long>()

        frequency.forEach { (value, count) ->
            val str = value.toString()
            val len = str.length
            if (value == 0L) {
                val v = blinked.getOrDefault(1L, 0L)
                blinked[1L] = v + count
            } else if (len % 2L == 0L) {
                val keyLeft = str.substring(0, len / 2).toLong()
                val valueLeft = blinked.getOrDefault(keyLeft, 0L)
                blinked[keyLeft] = valueLeft + count
                val keyRight = str.substring(len / 2).toLong()
                val valueRight = blinked.getOrDefault(keyRight, 0L)
                blinked[keyRight] = valueRight + count
            } else {
                val v = blinked.getOrDefault(value * 2024L, 0L)
                blinked[value * 2024L] = v + count
            }
        }

        return blinked
    }

    fun part2(): Long {
        // generate frequency map from input (looks like all inputs have unique values)
        var frequency = mutableMapOf<Long, Long>()
        list.forEach { frequency[it] = 1 }

        repeat(75) {
            frequency = blinkMap(frequency)
        }

        return frequency.values.sum()
    }
}

fun main() {
    val aoc = Day11("day11/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}