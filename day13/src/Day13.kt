import com.simonrules.aoc2024.utils.Point2D
import java.io.File

data class PrizeMachine(
    val distA: Point2D,
    val distB: Point2D,
    val prize: Point2D
)

class Day13(private val file: String) {
    private val machines = readInput()
    private val aCost = 3
    private val bCost = 1

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

    private fun cost(a: Long, b: Long): Long {
        return a * aCost + b * bCost
    }

    fun part1(): Int {
        var sum = 0
        machines.forEach { m ->
            val candidates = findCandidates(m.distA.x, m.distB.x, m.prize.x)
            val verified = verifyCandidates(m.distA.y, m.distB.y, m.prize.y, candidates)
            if (verified.isNotEmpty()) {
                sum += cost(verified.first().first.toLong(), verified.first().second.toLong()).toInt()
            }
        }

        return sum
    }

    private fun solveSimultaneous(m: PrizeMachine): Pair<Long, Long>? {
        val offset = 10000000000000L

        // e.g. 94a + 22b = 8400 (*67)
        val xa = m.distA.x.toLong()
        val xb = m.distB.x.toLong()
        val xt = m.prize.x + offset

        // e.g. 34a + 67b = 5400 (*22)
        val ya = m.distA.y.toLong()
        val yb = m.distB.y.toLong()
        val yt = m.prize.y + offset

        val xaa = xa * yb
        val xtt = xt * yb

        val yaa = ya * xb
        val ytt = yt * xb

        val a = (xtt - ytt) / (xaa - yaa)
        val aRem = (xtt - ytt) % (xaa - yaa)
        val b = (xt - (xa * a)) / xb
        val bRem = (xt - (xa * a)) % xb

        return if (aRem == 0L && bRem == 0L) {
            Pair(a, b)
        } else {
            null
        }
    }

    fun part2(): Long {
        var sum = 0L
        machines.forEach { m ->
            val solution = solveSimultaneous(m)
            if (solution != null) {
                sum += cost(solution.first, solution.second)
            }
        }

        return sum
    }
}

fun main() {
    val aoc = Day13("day13/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}