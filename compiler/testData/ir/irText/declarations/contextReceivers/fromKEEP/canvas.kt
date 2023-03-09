// FIR_IDENTICAL
// !LANGUAGE: +ContextReceivers
// TARGET_BACKEND: JVM_IR

// NO_SIGNATURE_DUMP
// ^KT-57435

interface Canvas {
    val suffix: String
}

interface Shape {
    context(Canvas)
    fun draw(): String
}

class Circle : Shape {
    context(Canvas)
    override fun draw() = "OK" + suffix
}

object MyCanvas : Canvas {
    override val suffix = ""
}

fun box() = with(MyCanvas) { Circle().draw() }
