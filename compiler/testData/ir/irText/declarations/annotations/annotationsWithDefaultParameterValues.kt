// FIR_IDENTICAL

// IGNORE_BACKEND_K2: JS_IR
// IGNORE_BACKEND_K2: JS_IR_ES6

annotation class A(val x: String = "", val y: Int = 42)

@A("abc", 123) fun test1() {}
@A("def") fun test2() {}
@A(x = "ghi") fun test3() {}
@A(y = 456) fun test4() {}
@A fun test5() {}
