import kotlin.math.min
import kotlin.time.measureTimedValue

fun main() {
    val day = "Day05"

    fun part1(input: List<String>): Int {
        val freshIngredients = input.takeWhile { it.isNotBlank() }.map {
            val values = it.split("-").toPair()
            values.first.toLong()..values.second.toLong()
        }
        val ingredients = input.takeLastWhile { it.isNotBlank() }.map(String::toLong)

        return ingredients.count { freshIngredients.any { fresh -> it in fresh } }
    }

    tailrec fun mergeWithExistingRanges(
        newFreshIngredients: MutableList<LongRange>,
        longs: LongRange
    ) {
        if (newFreshIngredients.isEmpty() || longs.first - newFreshIngredients.last().last > 1) {
            newFreshIngredients.add(longs)
        } else {
            val last = newFreshIngredients.removeLast()
            mergeWithExistingRanges(newFreshIngredients, min(last.first, longs.first)..longs.last)
        }
    }

    fun part2(input: List<String>): Long {
        val freshIngredients = input.takeWhile { it.isNotBlank() }.map {
            val values = it.split("-").toPair()
            values.first.toLong()..values.second.toLong()
        }.sortedBy { it.last }

        val newFreshIngredients = mutableListOf<LongRange>()
        freshIngredients.forEach {
            mergeWithExistingRanges(newFreshIngredients, it)
        }

        return newFreshIngredients.sumOf { it.last - it.first + 1}
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 3" }
        .println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 14" }
        .println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}