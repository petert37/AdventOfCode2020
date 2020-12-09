package day9

import java.io.File

fun main() {
    val input = File("src/day9/input.txt").readLines().map { it.toLong() }

    val invalidIndex = getInvalidIndex(input, 25) ?: return
    println("First invalid value: ${input[invalidIndex]}")

    val preamble = getSumPreamble(input, invalidIndex) ?: return
    val smallest = preamble.minOrNull() ?: return
    val largest = preamble.maxOrNull() ?: return
    println("Weakness: ${smallest + largest}")
}

fun getComponentNumbers(numbers: List<Long>, index: Int, preambleSize: Int): Pair<Long, Long>? {
    val preamble = numbers.subList(index - preambleSize, index)
    val target = numbers[index]
    for (i in preamble) {
        for (j in preamble) {
            if (i != j && i + j == target) {
                return i to j
            }
        }
    }
    return null
}

fun getInvalidIndex(input: List<Long>, preambleSize: Int): Int? {
    for (i in preambleSize until input.size) {
        getComponentNumbers(input, i, preambleSize) ?: return i
    }
    return null
}

fun getSumPreamble(input: List<Long>, targetIndex: Int): List<Long>? {
    val target = input[targetIndex]
    for (start in 0 until input.size - 1) {
        for (length in 2..(input.size - start)) {
            val preamble = input.subList(start, start + length)
            if (preamble.sum() == target) {
                return preamble
            }
        }
    }
    return null
}