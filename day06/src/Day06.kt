import java.io.File

data class Point2D(val x: Int, val y: Int)

enum class Direction { UP, RIGHT, DOWN, LEFT }

class Day06(file: String) {
    private val map = File(file).readLines()
    private val width = map[0].length
    private val height = map.size

    private fun getMapAt(p: Point2D): Char {
        return map[p.y][p.x]
    }

    private fun locateAgent(): Point2D {
        map.forEachIndexed { y, row ->
            // Agent is always up
            val x = row.indexOf('^')
            if (x != -1) {
                return Point2D(x, y)
            }
        }

        return Point2D(-1, -1) // not found
    }

    fun part1(): Int {
        val agentStartPos = locateAgent()
        val agentStartDirection = Direction.UP

        var pos = agentStartPos
        var direction = agentStartDirection

        val visited = mutableSetOf<Point2D>()
        visited.add(pos)

        do {
            val nextPos = when (direction) {
                Direction.UP -> Point2D(pos.x, pos.y - 1)
                Direction.DOWN -> Point2D(pos.x, pos.y + 1)
                Direction.LEFT -> Point2D(pos.x - 1, pos.y)
                Direction.RIGHT -> Point2D(pos.x + 1, pos.y)
            }

            // Don't continue if out of bounds
            if (nextPos.x !in 0..<width || nextPos.y !in 0..<height) {
                break
            }

            if (getMapAt(nextPos) == '#') {
                // collision, so rotate right instead of moving
                direction = when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.RIGHT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                }
            } else {
                // no collision, so move
                pos = nextPos
                visited.add(pos)
            }
        } while (true)

        return visited.size
    }

    fun part2(): Int {
        return 0
    }
}

fun main() {
    val aoc = Day06("day06/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
