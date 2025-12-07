import kotlin.run
import kotlin.time.measureTimedValue

fun main() {
    val day = "Day07"

    fun part1(input: List<String>): Number {
        val beams = mutableSetOf<Int>()
        var splits = 0
        input.forEach { row ->
            row.forEachIndexed { index, col ->
                if (col == 'S') beams.add(index)
                if (col == '^' && index in beams) {
                    beams.run {
                        remove(index)
                        add(index - 1)
                        add(index + 1)
                    }
                    splits++
                }
            }
        }
        return splits
    }

    fun part2(input: List<String>): Number {
        val beams = mutableMapOf<Int, Long>()
        input.forEach { row ->
            row.forEachIndexed { index, col ->
                if (col == 'S') beams[index] = 1
                if (col == '^' && index in beams) {
                    beams.run {
                        this += index - 1 to (this[index - 1] ?: 0) + (this[index] ?: 0)
                        this += index + 1 to (this[index + 1] ?: 0) + (this[index] ?: 0)
                        remove(index)
                    }
                }
            }
        }
        return beams.values.sum()
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 21" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 40" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}