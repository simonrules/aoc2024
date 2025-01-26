import com.simonrules.aoc2024.utils.Point2D
import java.io.File

data class PrizeMachine(
    val distA: Point2D,
    val distB: Point2D,
    val prize: Point2D
)

class Day13(private val file: String) {
    private val machines = readInput()

    private fun readInput(): List<PrizeMachine> {
        val regex = ".+: X.(\\d+), Y.(\\d+)".toRegex()
        val machineList = mutableListOf<PrizeMachine>()
        val blocks = File(file).readText().trim().split("\n\n")
        blocks.forEach {
            val lines = it.split('\n')
            val (distAX, distAY) = regex.matchEntire(lines[0])!!.destructured
            val (distBX, distBY) = regex.matchEntire(lines[1])!!.destructured
            val (prizeX, prizeY) = regex.matchEntire(lines[2])!!.destructured
            machineList.add(
                PrizeMachine(
                    Point2D(distAX.toInt(), distAY.toInt()),
                    Point2D(distBX.toInt(), distBY.toInt()),
                    Point2D(prizeX.toInt(), prizeY.toInt())
                )
            )
        }
        return machineList.toList()
    }

    private fun findCandidates(distA: Int, distB: Int, target: Int): List<Pair<Int, Int>> {
        val maxA = target / distA
        val maxB = target / distB
        val matches = mutableListOf<Pair<Int, Int>>()

        for (a in 0..maxA) {
            for (b in 0..maxB) {
                if (a * distA + b * distB == target) {
                    matches.add(Pair(a, b))
                }
            }
        }
        return matches.toList()
    }

    private fun verifyCandidates(
        distA: Int,
        distB: Int,
        target: Int,
        cands: List<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {
        val matches = mutableListOf<Pair<Int, Int>>()

        cands.forEach {
            if (it.first * distA + it.second * distB == target) {
                matches.add(it)
            }
        }

        return matches.toList()
    }

    private fun cost(a: Int, b: Int, aCost: Int, bCost: Int): Int {
        return a * aCost + b * bCost
    }

    private fun lowestCost(cands: List<Pair<Int, Int>>, aCost: Int, bCost: Int): Int? {
        val best = cands.minByOrNull { cost(it.first, it.second, aCost, bCost) }

        return if (best == null) {
            null
        } else {
            cost(best.first, best.second, aCost, bCost)
        }
    }

    fun part1(): Int {
        var sum = 0
        machines.forEach { m ->
            val candidates = findCandidates(m.distA.x, m.distB.x, m.prize.x)
            val verified = verifyCandidates(m.distA.y, m.distB.y, m.prize.y, candidates)
            val cost = lowestCost(verified, 3, 1)
            if (cost != null) {
                sum += cost
            }
        }

        return sum
    }

    fun part2(): Long {
        val offset = 10000000000000L
        return 0L
    }
}

fun main() {
    val aoc = Day13("day13/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}