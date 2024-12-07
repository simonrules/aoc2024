import java.io.File

data class Point2D(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point2D

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

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

    data class WalkResult(val visited: Int, val iterations: Int)

    private fun walkMap(
        agentPos: Point2D,
        agentDirection: Direction,
        maxIterations: Int = 100000,
        extraObstacle: Point2D? = null
    ): WalkResult {
        var pos = agentPos
        var direction = agentDirection

        val visited = mutableSetOf<Point2D>()
        visited.add(pos)

        var iteration = 0
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

            if (extraObstacle == nextPos || (getMapAt(nextPos) == '#')) {
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
            iteration++
        } while (iteration < maxIterations)

        return WalkResult(visited.size, iteration)
    }

    fun part1(): Int {
        return walkMap(locateAgent(), Direction.UP).visited
    }

    fun part2(): Int {
        val maxIterations = 10000
        val locations = mutableSetOf<Point2D>()

        val agentPos = locateAgent()

        for (y in 0..<height) {
            for (x in 0..<width) {
                val obstacleLocation = Point2D(x, y)

                if (walkMap(agentPos, Direction.UP, maxIterations, obstacleLocation).iterations == maxIterations) {
                    // walked in a loop
                    locations.add(obstacleLocation)
                }
            }
        }

        return locations.size
    }
}

fun main() {
    val aoc = Day06("day06/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
