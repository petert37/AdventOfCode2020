package day5

import java.io.File

fun main() {
    val input = File("src/day5/input.txt").readLines()
    val seats = input.map { Seat(it) }

    val highestIdSeat = seats.maxByOrNull { it.id }
    println("Highest seat id: ${highestIdSeat?.id}")

    val seatIds = seats.map { it.id }.sorted()
    for (i in seatIds.indices) {
        if (i < seatIds.size - 1 && seatIds[i] + 1 != seatIds[i + 1]) {
            println("My seat id: ${seatIds[i] + 1}")
        }
    }
}

class Seat(val code: String) {

    val row = calculatePosition(0, 127, 0..6, 'F')
    val column = calculatePosition(0, 7, 7..9, 'L')
    val id = row * 8 + column

    private fun calculatePosition(min: Int, max: Int, indices: IntRange, lowerHalfChar: Char): Int {
        var currentMin = min
        var currentMax = max
        for (i in indices) {
            val bounds = getBounds(currentMin, currentMax, code[i] == lowerHalfChar)
            currentMin = bounds.first
            currentMax = bounds.second
        }
        return currentMin
    }

    private fun getBounds(min: Int, max: Int, lowerHalf: Boolean): Pair<Int, Int> {
        return if (lowerHalf) {
            min to min + (max - min) / 2
        } else {
            min + (max - min) / 2 + 1 to max
        }
    }

}