package day11

import java.io.File

fun main() {
    val input = File("src/day11/input.txt").readLines()
    val layout = Layout(input.map { it.toCharArray().asList() })
    val occupiedBySurrounding = calculateOccupiedSeatCount(layout) { it.updateBySurrounding() }
    println(occupiedBySurrounding)
    val occupiedByVisible = calculateOccupiedSeatCount(layout) { it.updateByVisible() }
    println(occupiedByVisible)
}

fun calculateOccupiedSeatCount(layout: Layout, updateFunction: (layout: Layout) -> Layout): Int {
    var currentLayout = layout
    while (true) {
        val newLayout = updateFunction(currentLayout)
        if (newLayout == currentLayout) {
            break
        }
        currentLayout = newLayout
    }
    return currentLayout.getOccupiedSeatCount()
}

val directions = listOf(0 to -1, 1 to -1, 1 to 0, 1 to 1, 0 to 1, -1 to 1, -1 to 0, -1 to -1)

data class Layout(val seats: List<List<Char>>) {

    private val height = seats.size
    private val width = seats.getOrNull(0)?.size ?: 0

    fun getOccupiedSeatCount() = seats.sumBy { it.count { seat -> seat == '#' } }

    fun updateBySurrounding() = update(
        { x, y -> getSurroundingOccupiedCount(x, y) == 0 },
        { x, y -> getSurroundingOccupiedCount(x, y) >= 4 }
    )

    fun updateByVisible() = update(
        { x, y -> getVisibleOccupiedCount(x, y) == 0 },
        { x, y -> getVisibleOccupiedCount(x, y) >= 5 }
    )

    private fun get(x: Int, y: Int) = seats.getOrNull(y)?.getOrNull(x)

    private fun getSurroundingOccupiedCount(x: Int, y: Int): Int {
        return directions.count {
            get(x + it.first, y + it.second) == '#'
        }
    }

    private fun getVisibleOccupiedCount(x: Int, y: Int): Int {
        return directions.count {
            var i = 1
            while (true) {
                val seat = get(x + i * it.first, y + i * it.second) ?: break
                if (seat == '#') {
                    return@count true
                }
                if (seat == 'L') {
                    return@count false
                }
                i++
            }
            return@count false
        }
    }

    private fun update(
        occupySeatCondition: (x: Int, y: Int) -> Boolean,
        leaveSeatCondition: (x: Int, y: Int) -> Boolean
    ): Layout {
        val newSeats = mutableListOf<MutableList<Char>>()
        for (y in 0 until height) {
            for (x in 0 until width) {
                val seat = get(x, y) ?: continue
                val newSeat = when (seat) {
                    '.' -> '.'
                    'L' -> if (occupySeatCondition(x, y)) '#' else 'L'
                    '#' -> if (leaveSeatCondition(x, y)) 'L' else '#'
                    else -> error("Should not happen :)")
                }
                newSeats.add(y, newSeat)
            }
        }
        return Layout(newSeats)
    }

}

fun MutableList<MutableList<Char>>.add(y: Int, value: Char) {
    var list = this.getOrNull(y)
    if (list == null) {
        list = mutableListOf()
        this.add(list)
    }
    list.add(value)
}