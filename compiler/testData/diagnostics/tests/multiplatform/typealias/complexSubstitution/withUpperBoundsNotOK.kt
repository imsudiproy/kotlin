// FIR_IDENTICAL
// MODULE: m1-common
// FILE: common.kt

expect class E01<!NO_ACTUAL_FOR_EXPECT{JVM}!><T><!> {
    fun takeT(t: T): String
    fun takeInt(i: Int): String
}

expect class E02<!NO_ACTUAL_FOR_EXPECT{JVM}!><T : Number><!> {
    fun takeT(t: T): String
    fun takeInt(i: Int): String
}

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

actual typealias E01<!ACTUAL_WITHOUT_EXPECT!><T><!> = A01<T, Int>

actual typealias E02<!ACTUAL_WITHOUT_EXPECT!><T><!> = A02<T, Int>

class A01<T : Number, R> {
    fun takeT(t: T): String {}
    fun takeInt(i: R): String {}
}

class A02<T, R : Number> {
    fun takeT(t: T): String {}
    fun takeInt(i: R): String {}
}
