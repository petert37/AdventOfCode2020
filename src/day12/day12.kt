package day12

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/day12/input.txt").readLines()
    val moves = input.map { Move(it[0].toString(), it.substring(1, it.length).toInt()) }

    val shipWithDirection = ShipWithDirection(Position(0, 0), Direction.EAST)
    shipWithDirection.doMoves(moves)
    println(shipWithDirection.manhattanDistance())

    val shipWithWaypoint = ShipWithWaypoint(Position(0, 0), Position(10, 1))
    shipWithWaypoint.doMoves(moves)
    println(shipWithWaypoint.manhattanDistance())
}

enum class Direction(val char: String, val dx: Int, val dy: Int) {
    NORTH("N", 0, 1),
    EAST("E", 1, 0),
    SOUTH("S", 0, -1),
    WEST("W", -1, 0);

    fun turn(direction: String, value: Int): Direction {
        val offset = value / 90
        val index = this.ordinal + if (direction == "R") offset else -offset
        return values().getNormalized(index)
    }

    private fun <T> Array<T>.getNormalized(index: Int) =
        this[(index % this.size).let { if (it >= 0) it else this.size + it }]

    companion object {
        private val charMap = values().associateBy { it.char }
        fun fromChar(char: String) = charMap[char] ?: error("Unknown direction: $char")
    }
}

data class Move(val type: String, val value: Int)

data class Position(val x: Int, val y: Int) {

    fun move(direction: Direction, amount: Int) = Position(x + direction.dx * amount, y + direction.dy * amount)

    fun manhattanDistance() = abs(x) + abs(y)

    fun rotate(rotation: String, amount: Int): Position {
        var rotated = this
        repeat(amount / 90) {
            rotated = rotated.rotate(rotation)
        }
        return rotated
    }

    private fun rotate(rotation: String): Position {
        return when (rotation) {
            "L" -> Position(-this.y, this.x)
            "R" -> Position(this.y, -this.x)
            else -> error("Invalid rotation")
        }
    }

}

abstract class Ship(protected var position: Position) {

    fun doMoves(moves: List<Move>) {
        moves.forEach { doMove(it) }
    }

    fun manhattanDistance() = position.manhattanDistance()

    protected abstract fun doMove(move: Move)

}

class ShipWithDirection(position: Position, private var direction: Direction) : Ship(position) {

    override fun doMove(move: Move) {
        when (move.type) {
            "N", "S", "E", "W" -> position = position.move(Direction.fromChar(move.type), move.value)
            "L", "R" -> direction = direction.turn(move.type, move.value)
            "F" -> position = position.move(direction, move.value)
        }
    }

}

class ShipWithWaypoint(position: Position, private var waypoint: Position) : Ship(position) {

    override fun doMove(move: Move) {
        when (move.type) {
            "N", "S", "E", "W" -> waypoint = waypoint.move(Direction.fromChar(move.type), move.value)
            "L", "R" -> waypoint = waypoint.rotate(move.type, move.value)
            "F" -> position = Position(position.x + waypoint.x * move.value, position.y + waypoint.y * move.value)
        }
    }

}
