import kotlin.time.measureTimedValue

fun main() {
    val day = "Day11"

    fun <T> Map<T, Collection<T>>.countDistinctPaths(start: T, target: T, cache: HashMap<T, Long>): Long {
        fun travel(current: T): Long {
            if (current == target) return 1
            if (cache.containsKey(current)) return cache[current]!!

            cache[current] = this[current]?.sumOf(::travel) ?: 0
            return cache[current]!!
        }

        return travel(start)
    }

    fun graph(input: List<String>): HashMap<String, Collection<String>> {
        val graph = hashMapOf<String, Collection<String>>()
        input.forEach { line ->
            val occurrences = Regex("\\w{3}").findAll(line).toList().map { it.value }
            graph[occurrences.first()] = occurrences.drop(1)
        }
        return graph
    }

    fun part1(input: List<String>): Number {
        val graph = graph(input)
        return graph.countDistinctPaths("you", "out", HashMap())
    }

    fun part2(input: List<String>): Number {
        val graph = graph(input)
        return graph.countDistinctPaths("svr", "fft", HashMap()) *
                graph.countDistinctPaths("fft", "dac", HashMap()) *
                graph.countDistinctPaths("dac", "out", HashMap())
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 5" }
        .println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 2" }
        .println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}