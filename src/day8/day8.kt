package day8

import java.io.File

fun main() {
    val input = File("src/day8/input.txt").readLines()

    val instructions = input.map {
        val split = it.split(" ")
        Instruction(InstructionType.fromInstructionName(split[0]), split[1].toInt())
    }

    val program = Program(instructions)
    program.execute()
    val accumulator = program.accumulator
    println(accumulator)

    val successResult = findCorrectProgramResult(instructions)
    println(successResult)
}

private fun findCorrectProgramResult(instructions: List<Instruction>): Int? {
    instructions.forEachIndexed { index, instruction ->
        if (instruction.type == InstructionType.JMP || instruction.type == InstructionType.NOP) {
            val modified = instructions.toMutableList()
            modified[index] = if (instruction.type == InstructionType.JMP) {
                Instruction(InstructionType.NOP, instruction.argument)
            } else {
                Instruction(InstructionType.JMP, instruction.argument)
            }
            val modifiedProgram = Program(modified)
            val success = modifiedProgram.execute()
            if (success) {
                return modifiedProgram.accumulator
            }
        }
    }
    return null
}

class Program(private val instructions: List<Instruction>) {

    var pc = 0
    var accumulator = 0
    private val executedInstructions = mutableSetOf<Int>()

    fun execute(): Boolean {

        while (true) {
            if (executedInstructions.contains(pc)) {
                return false
            }
            val nextInstruction = instructions.getOrNull(pc) ?: return true
            executedInstructions.add(pc)
            executeInstruction(nextInstruction)
        }

    }

    private fun executeInstruction(instruction: Instruction) {
        when (instruction.type) {
            InstructionType.NOP -> {
                pc++
            }
            InstructionType.ACC -> {
                accumulator += instruction.argument
                pc++
            }
            InstructionType.JMP -> {
                pc += instruction.argument
            }
        }
    }

}

data class Instruction(val type: InstructionType, val argument: Int)

enum class InstructionType(val instructionName: String) {

    NOP("nop"),
    ACC("acc"),
    JMP("jmp");

    companion object {
        private val map = values().associateBy { it.instructionName }

        fun fromInstructionName(instructionName: String) = map[instructionName] ?: error("Unknown instruction0")
    }
}