package day10

import java.io.File

fun main() {
    val input = File("src/day10/input.txt").readLines().map { it.toInt() }
    val fullSortedInput = getFullSortedInput(input)

    val diffs = getDiffDistribution(fullSortedInput)
    println((diffs[1] ?: 0) * (diffs[3] ?: 0))

    val possiblePaths = getPossiblePaths(fullSortedInput)
    println(possiblePaths)
}

fun getDiffDistribution(input: List<Int>): Map<Int, Int> {
    val diffs = mutableMapOf<Int, Int>()
    input.forEachIndexed { index, value ->
        if (index < input.size - 1) {
            val diff = input[index + 1] - value
            diffs.compute(diff) { _, current ->
                (current ?: 0) + 1
            }
        }
    }
    return diffs
}

fun getFullSortedInput(input: List<Int>): List<Int> {
    val sorted = input.plus(0).sorted()
    val deviceJoltage = sorted.last() + 3
    return sorted.plus(deviceJoltage)
}

fun getPossiblePaths(input: List<Int>): Long? {
    val nodes = input.associateWith { 0L }.toMutableMap()
    nodes[0] = 1
    input.forEach { value ->
        for (i in value + 1..value + 3) {
            if (nodes[i] != null) {
                nodes.compute(i) { _, current ->
                    (current ?: 0) + (nodes[value] ?: 0)
                }
            }
        }
    }
    return nodes[input.last()]
}