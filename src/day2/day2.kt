package day2

import java.io.File

fun main() {
    val input = File("src/day2/input.txt").readLines()
    val passwordOptions = input.map {
        val parts = it.split(" ")
        val limits = parts[0].split("-").map { limit -> limit.toInt() }
        val char = parts[1][0]
        val password = parts[2]
        PasswordOption(limits[0], limits[1], char, password)
    }
    val validPasswordOptions = passwordOptions.filter { it.valid() }
    println("Valid count: ${validPasswordOptions.size}")
    val newValidPasswordOptions = passwordOptions.filter { it.newValid() }
    println("New valid count: ${newValidPasswordOptions.size}")
}

data class PasswordOption(val min: Int, val max: Int, val char: Char, val password: String) {

    fun valid() = password.count { it == char } in min..max

    fun newValid() = (password.getOrNull(min - 1) == char) xor (password.getOrNull(max - 1) == char)

}