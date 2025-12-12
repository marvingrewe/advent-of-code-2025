import kotlin.time.measureTimedValue

fun main() {
    val day = "Day12"

    class Region(inputString: String) {
        val area: Pair<Int, Int>
        val size: Int
        val shapeCount: Iterable<Int>
        init {
            val parts = inputString.split(' ')
            area = parts.first().dropLast(1).split('x').map { it.toInt() } .toPair()
            size = area.first * area.second
            shapeCount = parts.drop(1).map { it.toInt() }
        }
    }

    fun part1(input: List<String>): Number {
        val shapes = input.chunked(5) { chunk -> chunk.dropLast(1) }.takeWhile { !it[0].contains("x") }.map { it.drop(1) }
        val shapeSizes = shapes.map { it.sumOf { line -> line.count { char -> char == '#' } } }
        val trees = input.takeLastWhile { it.isNotBlank() }.map { Region(it) }

        val tooSmall = trees.count { tree ->
            tree.shapeCount.withIndex().sumOf { (index, count) -> count * shapeSizes[index] } > tree.size
        }.println()
        val largeEnough = trees.count { tree ->
            tree.shapeCount.sumOf { count -> count * 9 } <= tree.size
        }.println()
        val whoKnows = (trees.size - tooSmall - largeEnough).println()

        return largeEnough
    }

    fun part2(input: List<String>): Number {
        return input.size
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 2" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: " }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}