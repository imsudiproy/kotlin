// TARGET_BACKEND: JVM
// FIR_IDENTICAL

// NO_SIGNATURE_DUMP
// ^KT-57428

// FILE: stringVsT.kt
fun <T> useT(fn: () -> T) = fn()

fun testNoNullCheck() {
    useT { J.string() }
}

// FILE: J.java
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class J {
    public static String string() {
        return null;
    }

    public static @NotNull String notNullString() {
        return null;
    }
}
