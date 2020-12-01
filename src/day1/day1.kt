package day1

import java.io.File

fun main() {
    val input = File("src/day1/input.txt").readLines().map { it.toInt(10) }
    val pair = findPairAddingUpTo(input, 2020)
    if (pair != null) {
        println("${pair.first} * ${pair.second} = ${pair.first * pair.second}")
    }
    val triple = findTripleAddingUpTo(input, 2020)
    if (triple != null) {
        println("${triple.first} * ${triple.second} * ${triple.third} = ${triple.first * triple.second * triple.third}")
    }
}

fun findPairAddingUpTo(values: List<Int>, target: Int): Pair<Int, Int>? {
    for (i in values.indices) {
        for (j in values.indices) {
            if (i == j) continue
            if (values[i] + values[j] == target) return values[i] to values[j]
        }
    }
    return null
}

fun findTripleAddingUpTo(values: List<Int>, target: Int): Triple<Int, Int, Int>? {
    for (i in values.indices) {
        for (j in values.indices) {
            for (k in values.indices) {
                if (i == j || i == k || j == k) continue
                if (values[i] + values[j] + values[k] == target) return Triple(values[i], values[j], values[k])
            }
        }
    }
    return null
}