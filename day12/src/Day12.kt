package com.simonrules.aoc2024.day12

import com.simonrules.aoc2024.utils.Point2D
import java.io.File

class Day12(file: String) {
    private val map = File(file).readLines()
    private val width = map[0].length
    private val height = map.size

    private fun getMapAt(p: Point2D): Char {
        return if (!isInBounds(p)) {
            '.'
        } else {
            return map[p.y][p.x]
        }
    }

    private fun isInBounds(p: Point2D): Boolean {
        return ((p.x in 0..<width) && (p.y in 0..<height))
    }

    private fun findRegionArea(p: Point2D, type: Char, visited: Array<BooleanArray>): Int {
        if (!isInBounds(p)) {
            return 0
        }

        if (visited[p.y][p.x]) {
            return 0
        }

        if (type != getMapAt(p)) {
            return 0
        }

        visited[p.y][p.x] = true

        return 1 + findRegionArea(p.getNorth(), type, visited) +
                findRegionArea(p.getSouth(), type, visited) +
                findRegionArea(p.getEast(), type, visited) +
                findRegionArea(p.getWest(), type, visited)
    }

    private fun findRegionPerim(p: Point2D, type: Char, visited: Array<BooleanArray>): Int {
        // a square has a perimeter border everywhere except where it borders a square of the same type
        val north = p.getNorth()
        val south = p.getSouth()
        val east = p.getEast()
        val west = p.getWest()

        if (visited[p.y][p.x]) {
            return 0
        }

        visited[p.y][p.x] = true

        var myPerim = 4
        val northPerim = if (getMapAt(north) == type) {
            myPerim--
            findRegionPerim(north, type, visited)
        } else {
            0
        }
        val southPerim = if (getMapAt(south) == type) {
            myPerim--
            findRegionPerim(south, type, visited)
        } else {
            0
        }
        val eastPerim = if (getMapAt(east) == type) {
            myPerim--
            findRegionPerim(east, type, visited)
        } else {
            0
        }
        val westPerim = if (getMapAt(west) == type) {
            myPerim--
            findRegionPerim(west, type, visited)
        } else {
            0
        }
        return myPerim + northPerim + southPerim + eastPerim + westPerim
    }

    private fun findRegionPoints(p: Point2D, type: Char, visited: MutableSet<Point2D>) {
        if (!isInBounds(p)) {
            return
        }

        if (p in visited) {
            return
        }

        if (type != getMapAt(p)) {
            return
        }

        visited.add(p)

        findRegionPoints(p.getNorth(), type, visited)
        findRegionPoints(p.getSouth(), type, visited)
        findRegionPoints(p.getEast(), type, visited)
        findRegionPoints(p.getWest(), type, visited)
    }

    fun part1(): Int {
        val visited = Array(height) { BooleanArray(width) }

        var sum = 0
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (!visited[y][x]) {
                    val visited2 = Array(height) { BooleanArray(width) }

                    val p = Point2D(x, y)
                    val type = getMapAt(p)
                    val area = findRegionArea(p, type, visited)
                    val perim = findRegionPerim(p, type, visited2)
                    sum += area * perim
                }
            }
        }

        return sum
    }

    private fun countOutsideCorners(p: Point2D): Int {
        val self = getMapAt(p)

        val b = getMapAt(Point2D(p.x, p.y - 1)) == self
        val d = getMapAt(Point2D(p.x - 1, p.y)) == self
        val f = getMapAt(Point2D(p.x + 1, p.y)) == self
        val h = getMapAt(Point2D(p.x, p.y + 1)) == self

        return (if (!d && !b) 1 else 0) +
                (if (!b && !f) 1 else 0) +
                (if (!f && !h) 1 else 0) +
                (if (!h && !d) 1 else 0)
    }

    private fun countInsideCorners(p: Point2D): Int {
        val self = getMapAt(p)

        val a = getMapAt(Point2D(p.x - 1, p.y - 1)) == self
        val b = getMapAt(Point2D(p.x, p.y - 1)) == self
        val c = getMapAt(Point2D(p.x + 1, p.y - 1)) == self

        val d = getMapAt(Point2D(p.x - 1, p.y)) == self
        val f = getMapAt(Point2D(p.x + 1, p.y)) == self

        val g = getMapAt(Point2D(p.x - 1, p.y + 1)) == self
        val h = getMapAt(Point2D(p.x, p.y + 1)) == self
        val i = getMapAt(Point2D(p.x + 1, p.y + 1)) == self

        return (if (d && b && !a) 1 else 0) +
                (if (b && f && !c) 1 else 0) +
                (if (f && h && !i) 1 else 0) +
                (if (d && h && !g) 1 else 0)
    }

    private fun countRegionCorners(region: Set<Point2D>): Int {
        var sum = 0
        region.forEach { p ->
            sum += countInsideCorners(p)
            sum += countOutsideCorners(p)
        }
        return sum
    }

    fun part2(): Int {
        val regions = mutableListOf<Set<Point2D>>()
        val allVisited = mutableSetOf<Point2D>()

        // Find every region as a set of points
        for (y in 0..<height) {
            for (x in 0..<width) {
                val visited = mutableSetOf<Point2D>()

                val p = Point2D(x, y)
                if (p in allVisited) {
                    continue
                }
                findRegionPoints(p, getMapAt(p), visited)
                regions.add(visited)
                allVisited.addAll(visited)
            }
        }

        var sum = 0
        regions.forEach { r ->
            sum += r.count() * countRegionCorners(r)
        }

        return sum
    }
}

fun main() {
    val aoc = Day12("day12/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
