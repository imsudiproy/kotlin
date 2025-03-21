// RUN_PIPELINE_TILL: FRONTEND
// LATEST_LV_DIFFERENCE
// CHECK_TYPE

fun noCoercionLastExpressionUsedAsReturnArgument() {
    val a = {
        42
    }

    a checkType { _<() -> Int>() }
}

fun noCoercionBlockHasExplicitType() {
    val b: () -> Int = <!INITIALIZER_TYPE_MISMATCH!>{
        if (true) 42
    }<!>
}

fun noCoercionBlockHasExplicitReturn() {
    val c = l@{
        if (true) return@l 42

        <!INVALID_IF_AS_EXPRESSION!>if<!> (true) 239
    }
}

fun noCoercionInExpressionBody(): Unit = <!RETURN_TYPE_MISMATCH!>"hello"<!>
