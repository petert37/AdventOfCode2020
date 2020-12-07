package day7

import java.io.File

fun main() {

    val input = File("src/day7/input.txt").readLines()
    val rules = input.associate { line ->
        val bag = Bag(line.split(" ").let { "${it[0]} ${it[1]}" })
        val contentsString = line.split("contain")[1].trim()
        val contents = if (contentsString.startsWith("no"))
            emptyMap()
        else
            contentsString.split(",").map { it.trim().split(" ") }.associate { content ->
                Bag("${content[1]} ${content[2]}") to content[0].toInt()
            }
        bag to Rule(bag, contents)
    }

    val shinyGold = Bag("shiny gold")

    val canContainShinyGold = rules.filterValues {
        it.bag != shinyGold && it.bag.contains(shinyGold, rules)
    }
    println("Can contain shiny gold: ${canContainShinyGold.size}")

    val containedBagCount = shinyGold.getContainedBagCount(rules)
    println("Contained bag count: $containedBagCount")
}

data class Bag(val color: String) {

    fun contains(bag: Bag, rules: Rules): Boolean {
        val rule = rules[this] ?: return false
        if (rule.contents.keys.contains(bag)) {
            return true
        }
        return rule.contents.keys.any { it.contains(bag, rules) }
    }

    fun getContainedBagCount(rules: Rules): Int {
        val rule = rules[this] ?: return 0
        return rule.contents.map { (bag, amount) ->
            amount + amount * bag.getContainedBagCount(rules)
        }.sum()
    }

}

data class Rule(
    val bag: Bag,
    val contents: Map<Bag, Int>
)

typealias Rules = Map<Bag, Rule>