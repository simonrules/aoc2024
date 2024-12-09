package com.simonrules.aoc2024.utils

import kotlin.math.absoluteValue

data class Point2D(val x: Int, val y: Int) {
    fun getNorth(): Point2D {
        return Point2D(x, y - 1)
    }

    fun getSouth(): Point2D {
        return Point2D(x, y + 1)
    }

    fun getEast(): Point2D {
        return Point2D(x + 1, y)
    }

    fun getWest(): Point2D {
        return Point2D(x - 1, y)
    }

    fun move(dx: Int, dy: Int): Point2D {
        return Point2D(x + dx, y + dy)
    }

    fun manhattanDistance(other: Point2D): Int {
        return (x - other.x).absoluteValue + (y - other.y).absoluteValue
    }

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
