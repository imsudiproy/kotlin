@_exported import ExportedKotlinPackages
@_implementationOnly import KotlinBridges_feature
import KotlinRuntime
import KotlinRuntimeSupport

public extension ExportedKotlinPackages.oh.my.kotlin {
    public final class FeatureA: KotlinRuntime.KotlinBase, KotlinRuntimeSupport._KotlinBridged {
        public override init() {
            let __kt = oh_my_kotlin_FeatureA_init_allocate()
            super.init(__externalRCRef: __kt)
            oh_my_kotlin_FeatureA_init_initialize__TypesOfArguments__Swift_UInt__(__kt)
        }
        package override init(
            __externalRCRef: Swift.UInt
        ) {
            super.init(__externalRCRef: __externalRCRef)
        }
    }
}
