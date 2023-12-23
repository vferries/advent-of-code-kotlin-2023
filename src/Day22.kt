fun main() {
    val input = readInput("day22")
    countDisintegratableBricks(input).println()
    findBestBrickToDisintegrate(input).println()
}

fun findBestBrickToDisintegrate(input: List<String>): Int {
    var bricks = parseBricks(input)
    bricks = stabilize(bricks)
    return bricks.sumOf { brick ->
        val brickRemoved = bricks.minusElement(brick)
        val stab = stabilize(brickRemoved)
        val initialSet = brickRemoved.toSet()
        stab.count { !initialSet.contains(it) }
    }
}

data class Brick(val id: Int, val x: IntRange, val y: IntRange, val z: IntRange)

fun countDisintegratableBricks(input: List<String>): Int {
    var bricks = parseBricks(input)
    bricks = stabilize(bricks)
    return bricks.count { brick ->
        val brickRemoved = bricks.filter { it != brick }
        brickRemoved == gravityStep(brickRemoved)
    }
}

private fun stabilize(bricks: List<Brick>): List<Brick> {
    var bricks1 = bricks
    while (true) {
        val next = gravityStep(bricks1)
        if (next == bricks1) {
            break
        }
        bricks1 = next
    }
    return bricks1
}

fun gravityStep(bricks: List<Brick>): List<Brick> {
    val cubes = bricks.flatMap { brick ->
        brick.x.flatMap { x ->
            brick.y.flatMap { y ->
                brick.z.map { z ->
                    Triple(
                        x,
                        y,
                        z
                    )
                }
            }
        }
    }.toSet()
    return bricks.map { brick ->
        if (brick.z.min() == 1) {
            brick
        } else {
            val nexts = brick.x.flatMap { x -> brick.y.map { y -> Triple(x, y, brick.z.min() - 1) } }
            if (nexts.none { cubes.contains(it) }) {
                Brick(brick.id, brick.x, brick.y, (brick.z.min() - 1)..(brick.z.max() - 1))
            } else {
                brick
            }
        }
    }
}

fun parseBricks(input: List<String>): List<Brick> {
    val bricks = input.mapIndexed { index, line ->
        val (left, right) = line.split("~")
        val (x1, y1, z1) = left.split(",").map(String::toInt)
        val (x2, y2, z2) = right.split(",").map(String::toInt)
        Brick(index, x1..x2, y1..y2, z1..z2)
    }
    return bricks
}
