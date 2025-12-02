import kotlin.time.measureTimedValue

fun main() {
    val day = "Day02"

    fun findInvalidIds(
        input: List<String>,
        isInvalidId: (String) -> Boolean
    ): Long = input.first().split(",").fold(0L) { acc, string ->
        with(string.split("-").toPair()) {
            acc + (this.first.toLong()..this.second.toLong()).sumOf {
                if (isInvalidId(it.toString())
                ) it else 0
            }
        }
    }

    fun part1(input: List<String>): Long {
        return findInvalidIds(input) { id ->
            id.take(id.length / 2) == id.substring(
                id.length / 2
            )
        }
    }

    fun part2(input: List<String>): Long {
        return findInvalidIds(input) { id ->
            id.length.divisors().any { sequenceLength ->
                id.chunked(sequenceLength).distinct().size == 1
            }
        }
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 1227775554" }
        .println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 4174379265" }
        .println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}