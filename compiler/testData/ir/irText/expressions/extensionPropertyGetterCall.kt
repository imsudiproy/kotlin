// FIR_IDENTICAL

// IGNORE_BACKEND_K2: JS_IR
// IGNORE_BACKEND_K2: JS_IR_ES6

val String.okext: String get() = "OK"
fun String.test5() = okext
