import kotlin.math.pow
import kotlin.math.sqrt


typealias Vertex = Pair<Double, Double>

data class Circle(val center: Vertex, val radius: Double)

class Package(private val vertices: List<Vertex>) {
    val minRadiusFromZero: Double
        get() = sqrt(vertices.maxOf { (x, y) -> x * x + y * y })

    val minRadius: Double
        get() = welzl(vertices, listOf())!!.radius

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

private fun welzl(ps: List<Vertex>, rs: List<Vertex>): Circle? {
    var d: Circle?
    val p = ArrayList(ps)
    val r = ArrayList(rs)
    val lenR = r.size
    val lenP = p.size
    if (lenR == 3 || (lenP == 1 && (lenR == 0 || lenR == 1)) || (lenP == 0 && lenR == 2)) {
        d = trivial(p, lenP, r, lenR)
    } else {
        val first = p[0]
        p.removeAt(0)
        d = welzl(p, r)

        if (d != null && !isPointInsideCircle(d, first)) {
            r.add(first)
            d = welzl(p, r)
        }
    }
    return d
}

fun isPointInsideCircle(circle: Circle, point: Vertex): Boolean {
    val (xc, yc) = circle.center
    val (x, y) = point
    return sqrt(((x - xc).pow(2) + (y - yc).pow(2))) <= circle.radius
}

private fun trivial(ps: List<Vertex>, lenP: Int, rs: List<Vertex>, lenR: Int): Circle? {
    if (lenR == 3) {
        return getCircle(rs[0], rs[1], rs[2])
    } else if (lenP == 1 && lenR == 0) {
        return Circle(ps[0], 0.0)
    } else if (lenP == 0 && lenR == 2) {
        return getCircle(rs[0], rs[1])
    } else if (lenP == 1 && lenR == 1) {
        return getCircle(rs[0], ps[0])
    }
    return null
}

private fun getCircle(p1: Vertex, p2: Vertex): Circle {
    val center = (p1.first + p2.first) / 2.0 to (p1.second + p2.second) / 2.0
    val radius = sqrt(((p2.first - center.first).pow(2) + (p2.second - center.second).pow(2)))
    return Circle(center, radius)
}

private fun getCircle(p1: Vertex, p2: Vertex, p3: Vertex): Circle? {
    var p = p1
    var q = p2
    var r = p3
    //si les trois sont colineaires on passe
    if ((q.first - p.first) * (r.second - p.second) - (q.second - p.second) * (r.first - p.first) == 0.0) return null
    val tmp: Vertex
    //si p et q sont sur la meme ligne, ou p et r sont sur la meme ligne, on les echange
    if ((p.second == q.second) || (p.second == r.second)) {
        if (p.second == q.second) {
            tmp = p
            p = r //ici on est certain que p n'est sur la meme ligne de ni q ni r
            r = tmp //parse que les trois points sont non-colineaires
        } else {
            tmp = p
            p = q //ici on est certain que p n'est sur la meme ligne de ni q ni r
            q = tmp //parce que les trois points sont non-colineaires
        }
    }
    //on cherche les coordonnees du cercle circonscrit du triangle pqr
    //soit m=(p+q)/2 et n=(p+r)/2
    val mX = .5 * (p.first + q.first)
    val mY = .5 * (p.second + q.second)
    val nX = .5 * (p.first + r.first)
    val nY = .5 * (p.second + r.second)
    //soit y=alpha1*x+beta1 l'equation de la droite passant par m et perpendiculaire a la droite (pq)
    //soit y=alpha2*x+beta2 l'equation de la droite passant par n et perpendiculaire a la droite (pr)
    val alpha1 = (q.first - p.first) / (p.second - q.second)
    val beta1 = mY - alpha1 * mX;
    val alpha2 = (r.first - p.first) / (p.second - r.second)
    val beta2 = nY - alpha2 * nX
    //le centre c du cercle est alors le point d'intersection des deux droites ci-dessus
    val cX = (beta2 - beta1) / (alpha1 - alpha2)
    val cY = alpha1 * cX + beta1
    val cRadiusSquared = (p.first - cX) * (p.first - cX) + (p.second - cY) * (p.second - cY)

    return Circle(cX to cY, sqrt(cRadiusSquared))
}