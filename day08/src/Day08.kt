package com.simonrules.aoc2024.day08

import com.simonrules.aoc2024.utils.Point2D
import java.io.File

data class Antenna(val c: Char, val pos: Point2D)

class Day08(file: String) {
    private val width: Int
    private val height: Int

    private val antennas = mutableListOf<Antenna>()
    init {
        var y = 0
        var w = 0
        File(file).forEachLine { line ->
            line.forEachIndexed { x, c ->
                if (c != '.') {
                    antennas.add(Antenna(c, Point2D(x, y)))
                }
            }
            w = line.length
            y++
        }
        width = w
        height = y
    }

    private fun isInBounds(p: Point2D): Boolean {
        return p.x in 0..<width && p.y in 0..<height
    }

    private fun findAntinode(p0: Point2D, p1: Point2D): Point2D {
        val dx = p1.x - p0.x
        val dy = p1.y - p0.y

        return Point2D(p0.x - dx, p0.y - dy)
    }

    private fun findAllAntinodes(p0: Point2D, p1: Point2D): List<Point2D> {
        val dx = p1.x - p0.x
        val dy = p1.y - p0.y
        var p = p0
        val list = mutableListOf<Point2D>()

        while (isInBounds(p)) {
            list.add(p)
            p = Point2D(p.x - dx, p.y - dy)
        }

        return list
    }

    fun part1(): Int {
        val antinodes = mutableListOf<Point2D>()

        for (i in antennas.indices) {
            for (j in antennas.indices) {
                if ((i != j) && (antennas[i].c == antennas[j].c)) {
                    antinodes.add(findAntinode(antennas[i].pos, antennas[j].pos))
                }
            }
        }

        return antinodes.filter { isInBounds(it) }.toSet().size
    }

    fun part2(): Int {
        val antinodes = mutableSetOf<Point2D>()

        for (i in antennas.indices) {
            for (j in antennas.indices) {
                if ((i != j) && (antennas[i].c == antennas[j].c)) {
                    antinodes.addAll(findAllAntinodes(antennas[i].pos, antennas[j].pos))
                }
            }
        }

        return antinodes.size
    }
}

fun main() {
    val aoc = Day08("day08/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}