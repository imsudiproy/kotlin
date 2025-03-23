package org.jetbrains.kotlin.konan.target

import org.jetbrains.kotlin.konan.target.*

object S390x : KonanTarget(
    name = "s390x",
    targetTriple = "s390x-ibm-linux-gnu",
    defaultCpu = "s390x",
    pointerSize = 8,        // 64-bit pointers
    isBigEndian = true,       // IBM Z (s390x) is big-endian
    platform = KonanTargetPlatform.LINUX
) {
    override fun computeAdditionalCompilerFlags(): List<String> {
        // This flag ensures LLVM generates code for the s390x architecture.
        return listOf("-march=s390x")
    }
    
    // Add if any other runtime adjustment is required
}
