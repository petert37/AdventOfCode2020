package day13

import java.io.File
import kotlin.math.ceil

fun main() {
    val input = File("src/day13/input.txt").readLines()
    part1(input)
    part2(input)
}

fun part1(input: List<String>) {
    val arrivalTime = input[0].toInt()
    val busLines = input[1].split(",").filter { it != "x" }.map { it.toInt() }
    val nextBusArrivals = busLines.associateWith { ceil(arrivalTime.toDouble() / it.toDouble()).toInt() * it }
    val nextBusArrival = nextBusArrivals.minByOrNull { it.value }!!
    val waitTime = nextBusArrival.value - arrivalTime
    println("Next Bus ID: ${nextBusArrival.key}, time: ${nextBusArrival.value}, wait: $waitTime")
    println("Answer: ${nextBusArrival.key * waitTime}")
}

// t+44 is divisible by 37, 29 and 773 so t+44 is divisible by 37*29*773
// this only works with this particular input
fun part2(input: List<String>) {
    val busLines = mutableMapOf<Int, Int>()
    input[1].split(",").forEachIndexed { index, line ->
        if (line != "x") {
            busLines[index] = line.toInt()
        }
    }
    val divider = (37 * 29 * 773).toLong()
    var t = divider
    while (true) {
        if (t % divider == 0L && isMatch(t - 44, busLines)) {
            println("Part 2: ${t - 44}")
            break
        } else {
            t += divider
        }
    }
}

fun isMatch(t: Long, busLines: Map<Int, Int>): Boolean {
    busLines.forEach { (offset, line) ->
        if ((t + offset) % line != 0L) {
            return false
        }
    }
    return true
}
