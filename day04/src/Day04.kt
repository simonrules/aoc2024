import java.io.File

class Day04(file: String) {
    private val input = File(file).readLines()
    private val width = input[0].length
    private val height = input.size
    private val word = "XMAS"

    private fun getCharAt(x: Int, y: Int): Char {
        return if (x < 0 || y < 0 || x >= width || y >= height) {
            ' '
        } else {
            input[y][x]
        }
    }

    private fun findWordAt(startX: Int, startY: Int): Int {
        var words = 0
        for (dy in -1..1) {
            for (dx in -1..1) {
                var count = 0
                if ((dx == 0) && (dy == 0)) {
                    continue
                }

                var x = startX
                var y = startY
                for (n in word.indices) {
                    if (getCharAt(x, y) == word[n]) {
                        count++
                    }
                    x += dx
                    y += dy
                }

                if (count == word.length) {
                    words++
                }
            }
        }
        return words
    }

    private fun isKernelAt(startX: Int, startY: Int, kernel: String): Boolean {
        var i = 0
        var count = 0
        for (dy in 0..2) {
            for (dx in 0..2) {
                val x = startX + dx
                val y = startY + dy
                if ((kernel[i] == ' ') || (kernel[i] == getCharAt(x, y))) {
                    count++
                }
                i++
            }
        }
        return count == 9
    }

    fun part1(): Int {
        var count = 0

        for (y in 0..<height) {
            for (x in 0..<width) {
                count += findWordAt(x, y)
            }
        }

        return count
    }

    fun part2(): Int {
        val kernels = arrayOf(
            "M S" +
            " A " +
            "M S",
            "M M" +
            " A " +
            "S S",
            "S M" +
            " A " +
            "S M",
            "S S" +
            " A " +
            "M M"
        )

        var count = 0
        for (y in 0..<height - 2) {
            for (x in 0..<width - 2) {
                for (kernel in kernels) {
                    if (isKernelAt(x, y, kernel)) {
                        count++
                    }
                }
            }
        }
        return count
    }
}

fun main() {
    val aoc = Day04("day04/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
