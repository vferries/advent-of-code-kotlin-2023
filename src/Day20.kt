import Signal.HIGH
import Signal.LOW
import State.OFF
import State.ON

fun main() {
    val input = readInput("day20")
    countSignalsWhenPushingButton(input, 1000).println()
    minimumButtonsToRx(input).println()
}

fun minimumButtonsToRx(input: List<String>): Long {
    val modules = parseModules(input)

    val rxInputs = modules.entries.find { it.value.outputs.contains("rx") }!!.value.inputs.associateWith { 0 }.toMutableMap()
    var buttons = 0
    while (rxInputs.values.contains(0)) {
        buttons++
        val enqueued = mutableListOf(Triple("button", LOW, "broadcaster"))
        while (enqueued.isNotEmpty()) {
            val (source, signal, destination) = enqueued.removeFirst()
            if (rxInputs.keys.contains(destination) && signal == LOW && rxInputs[destination] == 0) {
                rxInputs[destination] = buttons
            }

            val destModule = modules.getOrDefault(destination, Untyped)
            enqueued.addAll(when (destModule) {
                is Broadcaster -> destModule.outputs.map { output -> Triple(destination, signal, output) }
                is FlipFlop -> {
                    if (signal == HIGH) {
                        listOf()
                    } else {
                        destModule.state = destModule.state.flip()
                        val signalToSend = if (destModule.state == ON) {
                            HIGH
                        } else {
                            LOW
                        }
                        destModule.outputs.map { output -> Triple(destination, signalToSend, output) }
                    }
                }

                Untyped -> listOf()
                is Conjunction -> {
                    destModule.previousInputs[source] = signal
                    val signalToSend =
                        if (destModule.inputs.all { input -> destModule.previousInputs.getValue(input) == HIGH }) {
                            LOW
                        } else {
                            HIGH
                        }
                    destModule.outputs.map { output -> Triple(destination, signalToSend, output) }
                }
            })
        }
    }
    return rxInputs.values.map { it.toLong() }.reduce(Long::times)
}

sealed class Module(val outputs: List<String>, var inputs: List<String> = listOf())

class Broadcaster(outputs: List<String>) : Module(outputs)

class FlipFlop(outputs: List<String>, var state: State = OFF) : Module(outputs)

class Conjunction(
    outputs: List<String>,
    val previousInputs: MutableMap<String, Signal> = mutableMapOf<String, Signal>().withDefault { LOW }
) : Module(outputs)

data object Untyped : Module(listOf())

enum class State {
    ON, OFF;

    fun flip(): State = if (this == ON) {
        OFF
    } else {
        ON
    }
}

enum class Signal {
    HIGH, LOW
}

fun countSignalsWhenPushingButton(input: List<String>, buttons: Int): Int {
    val modules = parseModules(input)
    var sentHighs = 0
    var sentLows = 0
    repeat(buttons) {
        val enqueued = mutableListOf(Triple("button", LOW, "broadcaster"))
        while (enqueued.isNotEmpty()) {
            val (source, signal, destination) = enqueued.removeFirst()
            when (signal) {
                HIGH -> sentHighs++
                LOW -> sentLows++
            }
            val destModule = modules.getOrDefault(destination, Untyped)
            enqueued.addAll(when (destModule) {
                is Broadcaster -> destModule.outputs.map { output -> Triple(destination, signal, output) }
                is FlipFlop -> {
                    if (signal == HIGH) {
                        listOf()
                    } else {
                        destModule.state = destModule.state.flip()
                        val signalToSend = if (destModule.state == ON) {
                            HIGH
                        } else {
                            LOW
                        }
                        destModule.outputs.map { output -> Triple(destination, signalToSend, output) }
                    }
                }

                Untyped -> listOf()
                is Conjunction -> {
                    destModule.previousInputs[source] = signal
                    val signalToSend =
                        if (destModule.inputs.all { input -> destModule.previousInputs.getValue(input) == HIGH }) {
                            LOW
                        } else {
                            HIGH
                        }
                    destModule.outputs.map { output -> Triple(destination, signalToSend, output) }
                }
            })
        }
    }
    return sentLows * sentHighs
}

private fun parseModules(input: List<String>): Map<String, Module> {
    val modules = input.associate {
        when {
            it.startsWith("&") -> {
                val (name, outputs) = it.drop(1).split(" -> ")
                name to Conjunction(outputs.split(", "))
            }

            it.startsWith("%") -> {
                val (name, outputs) = it.drop(1).split(" -> ")
                name to FlipFlop(outputs.split(", "))
            }

            it.startsWith("broadcaster ") -> {
                val (name, outputs) = it.split(" -> ")
                name to Broadcaster(outputs.split(", "))
            }

            else -> throw IllegalArgumentException("Unknown module $it")
        }
    }
    modules.forEach { (name, module) ->
        module.inputs = modules.filter { it.value.outputs.contains(name) }.map { it.key }
    }
    return modules
}
