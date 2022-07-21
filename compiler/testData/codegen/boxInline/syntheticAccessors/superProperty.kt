// IGNORE_BACKEND_K2: JVM_IR
// IGNORE_BACKEND_K2_MULTI_MODULE: JVM_IR JVM_IR_SERIALIZE
// !LANGUAGE: -ProhibitSuperCallsFromPublicInline
// FILE: 1.kt

package test

open class A {
    open val test = "OK"
}

object X : A() {
    override val test: String
        get() = "fail"

    inline fun doTest(): String {
        return super.test
    }
}

// FILE: 2.kt

import test.*

fun box(): String {
    return X.doTest()
}
