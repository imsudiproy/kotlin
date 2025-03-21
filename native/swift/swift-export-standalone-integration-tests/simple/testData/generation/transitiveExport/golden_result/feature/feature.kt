@file:kotlin.Suppress("DEPRECATION_ERROR")
@file:kotlin.native.internal.objc.BindClassToObjCName(oh.my.kotlin.FeatureA::class, "22ExportedKotlinPackages2ohO2myO6kotlinO7featureE8FeatureAC")

import kotlin.native.internal.ExportedBridge
import kotlinx.cinterop.*
import kotlinx.cinterop.internal.convertBlockPtrToKotlinFunction

@ExportedBridge("oh_my_kotlin_FeatureA_init_allocate")
public fun oh_my_kotlin_FeatureA_init_allocate(): kotlin.native.internal.NativePtr {
    val _result = kotlin.native.internal.createUninitializedInstance<oh.my.kotlin.FeatureA>()
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("oh_my_kotlin_FeatureA_init_initialize__TypesOfArguments__Swift_UInt__")
public fun oh_my_kotlin_FeatureA_init_initialize__TypesOfArguments__Swift_UInt__(__kt: kotlin.native.internal.NativePtr): Unit {
    val ____kt = kotlin.native.internal.ref.dereferenceExternalRCRef(__kt)!!
    kotlin.native.internal.initInstance(____kt, oh.my.kotlin.FeatureA())
}
