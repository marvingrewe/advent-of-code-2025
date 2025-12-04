import kotlin.time.measureTimedValue

fun main() {
    val day = "Day04"

    fun part1(input: List<String>): Int {
        var count = 0
        input.forEachIndexed { rowIndex, line ->
            line.forEachIndexed { columnIndex, char ->
                if (char == '@' && input.getNeighbors(rowIndex, columnIndex).count { it == '@' } < 4) {
                    count++
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var map = input
        var sum = 0
        var paperRemovable = true
        while (paperRemovable) {
            var count = 0
//            map.forEach(::println)
//            println()
            val newMap = map.indices.map { row -> map[row].indices.map { col -> map[row][col] }.toMutableList() }
            map.forEachIndexed { rowIndex, line ->
                line.forEachIndexed { columnIndex, char ->
                    if (char == '@' && map.getNeighbors(rowIndex, columnIndex).count { it == '@' } < 4) {
                        count++
                        newMap[rowIndex][columnIndex] = '.'
                    }
                }
            }
            sum += count
            map = newMap.map { line -> line.joinToString("") }

            if (count == 0) paperRemovable = false
        }
        return sum
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 13" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 43" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}