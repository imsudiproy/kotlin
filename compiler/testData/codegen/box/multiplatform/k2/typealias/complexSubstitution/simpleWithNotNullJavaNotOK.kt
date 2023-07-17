// FIR_IDENTICAL
// MODULE: m1-common
// FILE: common.kt

expect class E01<T> {
    fun takeT(t: T)
    fun takeInt(i: Int)
}

expect class E02<T, R> {
    fun takeT(t: T)
    fun takeInt(i: Int)
}

interface Base<T> {
    fun takeT(t: T)
}

expect class E03<T> : Base<T> {
    fun takeInt(i: Int)
}

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

actual typealias <!NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS!>E01<!><T> = A01<T, Int>

actual typealias <!NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS!>E02<!><T, R> = A01<T, Int>

// Currently this matching is fine as we do not yet check expect fake overrides =(
actual typealias E03<T> = A01<T, Int>

// FILE: A01.java

import org.jetbrains.annotations.*;

public class A01<T, R> implements Base<T> {
    public void takeT(final @NotNull T t) {}
    public void takeInt(final @NotNull R i) {}
}
