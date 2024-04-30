// WITH_STDLIB
interface I<T, V> {
    fun foo(x: T): V
}

class C : I<Result<Any?>, Any?> {
    override fun foo(x: Result<Any?>) = x.getOrNull()
}

fun box1() = (C() as I<Result<Any?>, Any?>).foo(Result.success("OK"))

fun box() = box1() ?: "null"
