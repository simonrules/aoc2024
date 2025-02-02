import com.simonrules.aoc2024.utils.Point2D
import java.io.File

data class Robot(val pos: Point2D, val vel: Point2D)

class Day14(file: String) {
    val robots = File(file).readLines().map { parseRobot(it) }

    private fun parsePoint(input: String): Point2D {
        val parts = input.drop(2).split(",")
        return Point2D(parts[0].toInt(), parts[1].toInt())
    }

    private fun parseRobot(line: String): Robot {
        val points = line.split(" ")

        return Robot(parsePoint(points[0]), parsePoint(points[1]))
    }

    private fun moveRobot(robot: Robot, iter: Int, w: Int, h: Int): Robot {
        val x = (robot.pos.x + robot.vel.x * iter).mod(w)
        val y = (robot.pos.y + robot.vel.y * iter).mod(h)

        return Robot(Point2D(x, y), robot.vel)
    }

    private fun quadrantScore(r: List<Robot>, w: Int, h: Int): Int {
        val a = r.count { it.pos.x < w / 2 && it.pos.y < h / 2 }
        val b = r.count { it.pos.x > w / 2 && it.pos.y < h / 2 }
        val c = r.count { it.pos.x < w / 2 && it.pos.y > h / 2 }
        val d = r.count { it.pos.x > w / 2 && it.pos.y > h / 2 }

        return a * b * c * d
    }

    private fun printRobots(robots: List<Robot>, w: Int, h: Int) {
        for (i in 0..<h) {
            for (j in 0..<w) {
                val r = robots.any { it.pos == Point2D(j, i) }
                if (r) print('*') else print(' ')
            }
            println()
        }
        println()
    }

    fun part1(): Int {
        val w = 101
        val h = 103
        val newRobots = robots.map { moveRobot(it, 100, w, h) }
        
        return quadrantScore(newRobots, w, h)
    }

    fun part2() {
        val w = 101
        val h = 103
        var iter = 77
        var newRobots = robots.map { moveRobot(it, 77, w, h) }
        for (i in 1..100) {
            println(iter)
            printRobots(newRobots, w, h)
            iter += 101
            newRobots = newRobots.map { moveRobot(it, 101, w, h) }
        }
    }
}

fun main() {
    val aoc = Day14("day14/input.txt")
    println(aoc.part1())
    aoc.part2()
}
