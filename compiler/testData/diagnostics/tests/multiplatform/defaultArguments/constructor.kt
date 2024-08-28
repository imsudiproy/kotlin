// MODULE: m1-common
// FILE: common.kt

expect class Ok(x: Int, y: String = "")

<!EXPECT_ACTUAL_INCOMPATIBILITY{JVM}!>expect class FailX<!EXPECT_ACTUAL_INCOMPATIBILITY{JVM}!>(x: Int, y: String = "")<!><!>

<!EXPECT_ACTUAL_INCOMPATIBILITY{JVM}!>expect class FailY<!EXPECT_ACTUAL_INCOMPATIBILITY{JVM}!>(x: Int, y: String = "")<!><!>

fun test() {
    Ok(42)
    Ok(42, "OK")
}

// MODULE: m2-jvm()()(m1-common)
// FILE: jvm.kt

actual class Ok actual constructor(x: Int, y: String)

actual class FailX actual constructor(<!ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS!>x: Int = 0<!>, y: String)

actual class FailY actual constructor(x: Int, <!ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS!>y: String = ""<!>)
