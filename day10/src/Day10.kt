package com.simonrules.aoc2024.day10

import com.simonrules.aoc2024.utils.Point2D
import java.io.File

class Day10(file: String) {
    private val map = File(file).readLines()
    private val width = map[0].length
    private val height = map.size

    private fun getMapAt(p: Point2D): Int {
        return map[p.y][p.x].digitToInt()
    }

    private fun isInBounds(p: Point2D): Boolean {
        return (p.x in 0..<width && p.y in 0..<height)
    }

    private fun getTrailheads(): Set<Point2D> {
        val trailheads = mutableSetOf<Point2D>()
        for (y in 0..<height) {
            for (x in 0..<width) {
                val p = Point2D(x, y)
                if (getMapAt(p) == 0) {
                    trailheads.add(p)
                }
            }
        }
        return trailheads.toSet()
    }

    private fun bfs(start: Point2D): Int {
        // Use BFS because every step involves a climb of 1 so all paths proceed at the same level
        val queue = mutableListOf<Point2D>()
        val visited = mutableSetOf<Point2D>()
        var count = 0
        queue.add(start)

        while (queue.isNotEmpty()) {
            val p = queue.removeFirst()
            if (p in visited) {
                continue
            }
            visited.add(p)

            val level = getMapAt(p)
            if (level == 9) {
                // found destination
                count++
            } else {
                // try other directions
                val north = p.getNorth()
                if (isInBounds(north) && getMapAt(north) == level + 1) {
                    queue.addLast(north)
                }
                val south = p.getSouth()
                if (isInBounds(south) && getMapAt(south) == level + 1) {
                    queue.addLast(south)
                }
                val east = p.getEast()
                if (isInBounds(east) && getMapAt(east) == level + 1) {
                    queue.addLast(east)
                }
                val west = p.getWest()
                if (isInBounds(west) && getMapAt(west) == level + 1) {
                    queue.addLast(west)
                }
            }
        }

        return count
    }

    fun part1(): Int {
        val trailheads = getTrailheads()
        var sum = 0
        trailheads.forEach {
            sum += bfs(it)
        }
        return sum
    }

    private fun dfs(p: Point2D): Int {
        // Use DFS because we need to find every possible trail
        val level = getMapAt(p)
        if (level == 9) {
            return 1
        }
        var sum = 0
        val north = p.getNorth()
        if (isInBounds(north) && getMapAt(north) == level + 1) {
            sum += dfs(north)
        }
        val south = p.getSouth()
        if (isInBounds(south) && getMapAt(south) == level + 1) {
            sum += dfs(south)
        }
        val east = p.getEast()
        if (isInBounds(east) && getMapAt(east) == level + 1) {
            sum += dfs(east)
        }
        val west = p.getWest()
        if (isInBounds(west) && getMapAt(west) == level + 1) {
            sum += dfs(west)
        }
        return sum
    }

    fun part2(): Int {
        val trailheads = getTrailheads()
        var sum = 0
        trailheads.forEach {
            sum += dfs(it)
        }
        return sum
    }
}

fun main() {
    val aoc = Day10("day10/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
