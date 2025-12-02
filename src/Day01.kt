import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    val day = "Day01"

    fun rotateDial(acc: Int, string: String): Int =
        acc + (if (string[0] == 'L') -1 else 1) * string.substring(1).toInt()

    fun part1(input: List<String>): Int {
        var count = 0
        input.fold(50) { acc, string ->
            rotateDial(acc, string)
                .also { if (it % 100 == 0) count++ }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        input.fold(50050) { acc, string ->
            rotateDial(
                acc,
                string
            ).also {
                count += abs(acc / 100 - it / 100) +
                        (if (it % 100 == 0 && string[0] == 'L') 1 else 0) -
                        (if (acc % 100 == 0 && string[0] == 'L') 1 else 0)
            }
        }
        return count
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 3" }
        .println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 6" }
        .println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}