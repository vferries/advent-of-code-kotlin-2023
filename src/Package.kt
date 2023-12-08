import kotlin.math.min
import kotlin.math.sqrt

typealias Vertex = Pair<Double, Double>

class Package(private val vertices: List<Vertex>) {
    val minRadiusFromZero: Double
        get() = sqrt(vertices.maxOf { (x, y) -> x * x + y * y })

    val minRadius: Double
        get() {
            var minRadius = Double.MAX_VALUE
            // On teste les cercles qui passent par deux points
            for (i in vertices.indices) {
                val (x1, y1) = vertices[i]
                for (j in (i + 1..vertices.lastIndex)) {
                    val (x2, y2) = vertices[j]
                    val (xc, yc) = (x1 + x2) / 2.0 to (y1 + y2) / 2.0
                    println("center 2 = $xc, $yc")
                    val radius = sqrt(((x2 - xc) * (x2 - xc) + (y2 - yc) * (y2 - yc)))
                    //Check that all points are contained
                    if (containAllPoints(xc to yc, radius)) {
                        minRadius = min(radius, minRadius)
                    }
                }
            }
            // Puis 3 points
            for (i in vertices.indices) {
                val (x1, y1) = vertices[i]
                for (j in (i + 1..vertices.lastIndex)) {
                    val (x2, y2) = vertices[j]
                    for (k in (j + 1..vertices.lastIndex)) {
                        val (x3, y3) = vertices[k]

                        //do your magic

                        var xc = (((x3*x3-x2*x2+y3*y3-y2*y2)/(2*(y3-y2))) - (x2*x2-x1*x1+y2*y2-y1*y1)/(2*(y2-y1))) / (((x2-x1)/(y2-y1))-((x3-x2)/(y3-y2)))
                        var yc = -((x2-x1)/(y2-y1))*xc + ((x2*x2 -x1*x1 + y2*y2 - y1*y1)/(2*(y2-y1)))
                        if (xc.isNaN()) {
                            val (xt, yt) = x2 to y2
                            val (x2, y2) = x3 to y3
                            val (x3, y3) = xt to yt
                            xc = (((x3*x3-x2*x2+y3*y3-y2*y2)/(2*(y3-y2))) - (x2*x2-x1*x1+y2*y2-y1*y1)/(2*(y2-y1))) / (((x2-x1)/(y2-y1))-((x3-x2)/(y3-y2)))
                            yc = -((x2-x1)/(y2-y1))*xc + ((x2*x2 -x1*x1 + y2*y2 - y1*y1)/(2*(y2-y1)))
                        }
                        println("center 3 = $xc, $yc")
                        val radius = sqrt(((x2 - xc) * (x2 - xc) + (y2 - yc) * (y2 - yc)))
                        //Check that all points are contained
                        if (containAllPoints(xc to yc, radius)) {
                            minRadius = min(radius, minRadius)
                        }
                    }
                }
            }
            return minRadius
        }

    private fun containAllPoints(center: Vertex, radius: Double): Boolean {
        val (xc, yc) = center
        return vertices.all { (x, y) -> sqrt(((x - xc) * (x - xc) + (y - yc) * (y - yc))) <= radius }
    }

    companion object {
        fun fromString(line: String): Package = Package(
            line.replace("(", "").replace(")", "")
                .split(", ")
                .map(String::toDouble)
                .chunked(2)
                .map { it[0] to it[1] })
    }
}