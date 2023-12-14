fun main() {
    val key = "4b7346456475"
    println(key.chunked(2).map { it.toInt(16) }.map { Char(it) }.joinToString(""))
}