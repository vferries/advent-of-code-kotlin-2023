import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
fun main() {
    /*val zipped =
        "504b0304140000000800057e81574dc5badb8b0000009800000008000000666c61672e74787425cec10e82201800e0472a6b6d5d3afc1a39b52804e58f1b8ccd8c98e8da129fbeb64edff563af305cc529955c096957c914f7000cef407e40dac111600d2a0ce5ad2d72ad2dfaaa16a45019f8dd27b65b546e949c3f99a69e91c5945c7695cbcc8537348c593fb75e8b122de6e14c625c746836efb4778c196d924027a4a35e70b6b1160fe6e8bf70f802504b01021403140000000800057e81574dc5badb8b00000098000000080000000000000000000000800100000000666c61672e747874504b0506000000000100010036000000b10000000000"
    val octets = zipped.chunked(2).map { it.toInt(16) }.map { it.toByte() }.toByteArray()
    val zipIs = ZipInputStream(ByteArrayInputStream(octets))
    FileOutputStream(File("zipped.zip")).write(octets)
    var zipEntry: ZipEntry? = zipIs.nextEntry*/

    /*
    val zipContent = "QlpoOTFBWSZTWd/1ry8AAQXYAEAAQABgADAA0AZpoJPVIGaadXmKRTEIZCAm5wyV3XZkqWSSjQaNmQEzbJSWgKkCbMSUNpqCixVmaTJXdXGpLEyyzapU2tBikQQbab1pNrXNqazXxdyRThQkN/1ry8A="
    val result = Base64.Mime.decode(zipContent)
    FileOutputStream(File("zipped.bzip")).write(result)
     */

    /*
    val zipped = "110110 110110 110110 1100011 110110 110001 110110 110111 110111 1100010 110110 111001 110110 1100101 110011 110111 110010 1100110 110100 1100011 110110 111001 110110 1100101 110111 110101 110111 110011 110101 1100110 110101 110100 110011 110000 110111 110010 110111 110110 110011 110100 110110 1100011 110110 110100 110111 110011 110010 1100001 110111 110010 110110 110101 110110 110111 110110 110101 110111 111000 110010 1100110 110110 111000 110110 110001 110111 110011 110110 111000 110011 1100100 110100 110000 110110 110011 110110 1100010 110111 1100100"
    val octets = zipped.split(" ").map { it.toByte(2) }.toByteArray()
    FileOutputStream(File("zipped.wtf")).write(octets)
     */

    val next = "666c61677b696e372f4c696e75735f54307276346c64732a72656765782f686173683d40636b7d"
    val octets = next.chunked(2).map { it.toInt(16) }.map { it.toByte() }.toByteArray()
    FileOutputStream(File("hexa.dontknow")).write(octets)

}