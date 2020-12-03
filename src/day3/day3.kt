package day3

import java.io.File

fun main() {

    val input = File("src/day3/input.txt").readLines()
    val forestMap = ForestMap(input)

    val trees = countTreesOnSlope(forestMap, 3, 1)
    println("Trees: $trees")

    val product = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        .map { countTreesOnSlope(forestMap, it.first, it.second) }
        .reduce { a, b -> a * b }
    println("Product of slopes: $product")

}

fun countTreesOnSlope(forestMap: ForestMap, right: Int, down: Int): Int {
    var x = 0
    var y = 0
    var trees = 0

    while (true) {
        x += right
        y += down
        if (y >= forestMap.height) {
            break
        }
        if (forestMap.isTree(x, y)) {
            trees++
        }
    }

    return trees
}

class ForestMap(input: List<String>) {

    private val forest: List<List<String>> = input.map { it.map { char -> "$char" } }
    private val width = forest.getOrNull(0)?.size ?: 0
    val height = forest.size

    fun get(x: Int, y: Int) = forest.getOrNull(y)?.getOrNull(x % width)

    fun isTree(x: Int, y: Int) = get(x, y) == "#"

}