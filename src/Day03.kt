import kotlin.time.measureTimedValue

fun main() {
    val day = "Day03"

    fun joltageOfLine(line: String): Int {
        val highestChar = line.dropLast(1).max()
        val indexOfFirst = line.indexOfFirst { it == highestChar }
        val nextChar = line.substring(indexOfFirst + 1).max()
        return "$highestChar$nextChar".toInt()
    }

    fun joltageOfLineRec(line: String, remainingChars: Int): String {
        if (remainingChars == 1) return line.max().toString()
        val indexOfMax = line.dropLast(remainingChars - 1).indices.maxBy { line[it] }
        val highestChar = line[indexOfMax]
        return  highestChar.toString() + joltageOfLineRec(line.substring(indexOfMax + 1), remainingChars - 1)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf(::joltageOfLine)
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { joltageOfLineRec(it, 12).toLong() }
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 357" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 3121910778619" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}