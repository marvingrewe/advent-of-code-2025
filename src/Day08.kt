import kotlin.collections.map
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.measureTimedValue

fun main() {
    val day = "Day08"

    fun Triple<Int, Int, Int>.distanceTo(other: Triple<Int, Int, Int>): Double {
        return sqrt(
            (this.first - other.first).toDouble().pow(2) +
                    (this.second - other.second).toDouble().pow(2) +
                    (this.third - other.third).toDouble().pow(2)
        )
    }

    fun part1(input: List<String>): Number {
        val boxes = input.toIntLists(Regex("\\W")).map { Triple(it[0], it[1], it[2]) }
        val circuits = boxes.map { box -> hashSetOf(box) }.toMutableList()
        val distances = (1..boxes.lastIndex).flatMap { i ->
            (0..<i).map { j ->
                boxes[i] to boxes[j] to boxes[i].distanceTo(boxes[j])
            }
        }.sortedBy { it.second }

        distances.take(1000).forEach { (nodes, _) ->
            val firstCircuit = circuits.find { it.contains(nodes.first) }
            val secondCircuit = circuits.find { it.contains(nodes.second) }
            if (firstCircuit != secondCircuit) {
                circuits.remove(firstCircuit)
                circuits.remove(secondCircuit)
                circuits.add((firstCircuit!! + secondCircuit!!) as HashSet<Triple<Int, Int, Int>>)
            }
        }

        return circuits.sortedByDescending { it.count() }.take(3).fold(1) { acc, list -> acc * list.size }
    }

    fun part2(input: List<String>): Number {
        val boxes = input.toIntLists(Regex("\\W")).map { Triple(it[0], it[1], it[2]) }
        val circuits = boxes.map { box -> hashSetOf(box) }.toMutableList()
        val distances = (1..boxes.lastIndex).flatMap { i ->
            (0..<i).map { j ->
                boxes[i] to boxes[j] to boxes[i].distanceTo(boxes[j])
            }
        }.sortedBy { it.second }

        distances.forEach { (nodes, _) ->
            val firstCircuit = circuits.find { it.contains(nodes.first) }
            val secondCircuit = circuits.find { it.contains(nodes.second) }
            if (firstCircuit != secondCircuit) {
                circuits.remove(firstCircuit)
                circuits.remove(secondCircuit)
                circuits.add((firstCircuit!! + secondCircuit!!) as HashSet<Triple<Int, Int, Int>>)

                if (circuits.size == 1) {
                    return nodes.first.first.toLong() * nodes.second.first
                }
            }
        }

        return -1 // unreachable default
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 40" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: " }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}