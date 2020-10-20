import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets.US_ASCII

val ALPHABET = arrayListOf('>', '<', '+', '-', '.', ',', '[', ']')
val array = CharArray(30000)
var pointer = 0

fun main(args: Array<String>) {
    val commandLine = InputStreamReader(FileInputStream(args[0]), US_ASCII).readText().filter { ALPHABET.contains(it) }
    pointer = if(args[1].toBoolean()) 15000 else 0
    for (i in array.indices)
        array[i] = '\u0000'
    resolveBrackets(commandLine)
}

fun resolveBrackets(s: String) {
    val openBracketIndex = s.indexOf(ALPHABET[6])
    if (openBracketIndex == -1)
        runChars(s)
    else {
        val closingBracketIndex = findClosingBracketIndex(s)
        runChars(s.substring(0, openBracketIndex))
        var whilePointer = pointer
        while (array[whilePointer].toInt() != 0) {
            resolveBrackets(s.substring(openBracketIndex + 1, closingBracketIndex))
            whilePointer = pointer
        }
        resolveBrackets(s.substring(closingBracketIndex + 1))
    }
}

fun runChars(commandLine: String) {
    commandLine.forEach {
        when (it) {
            ALPHABET[0] -> pointer++
            ALPHABET[1] -> pointer--
            ALPHABET[2] -> array[pointer]++
            ALPHABET[3] -> array[pointer]--
            ALPHABET[4] -> print(array[pointer])
            ALPHABET[5] -> array[pointer] = System.`in`.read().toChar()
            else -> err()
        }
    }
}

fun findClosingBracketIndex(s: String): Int {
    var i = 0
    s.forEachIndexed { index, c ->
        if(i < 0)
            err()
        if (c == ALPHABET[6])
            i++
        else if (c == ALPHABET[7] && --i == 0)
            return index
    }
    err()
}

fun err(): Nothing = error("Program stopped on cell $pointer")