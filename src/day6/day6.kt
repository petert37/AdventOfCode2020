package day6

import java.io.File

fun main() {

    val input = File("src/day6/input.txt").readLines()
    val groups = mutableListOf<Group>()
    val people = mutableListOf<Person>()

    for (i in input.indices) {
        val line = input[i]
        if (line.isNotEmpty()) {
            people.add(Person(line))
        }
        if (i == input.size - 1 || line.isEmpty()) {
            groups.add(Group(people.toList()))
            people.clear()
        }
    }

    val union = groups.map { it.getCharUnion().size }.sum()
    println("Union: $union")

    val intersection = groups.map { it.getCharIntersection().size }.sum()
    println("Intersection: $intersection")

}

data class Person(val value: String)

data class Group(val people: List<Person>) {

    fun getCharUnion() = people.flatMap { it.value.toList() }.distinct()

    fun getCharIntersection() = people.map { it.value.toSet() }.reduce { a, b -> a.intersect(b) }.toList()

}