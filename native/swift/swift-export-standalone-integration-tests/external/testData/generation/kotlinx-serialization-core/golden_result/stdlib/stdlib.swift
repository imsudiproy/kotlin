@_exported import ExportedKotlinPackages
import KotlinRuntimeSupport
import KotlinRuntime
@_implementationOnly import KotlinBridges_stdlib

public extension ExportedKotlinPackages.kotlin {
    public final class IntArray: KotlinRuntime.KotlinBase, KotlinRuntimeSupport._KotlinBridged {
        public var size: Swift.Int32 {
            get {
                return kotlin_IntArray_size_get(self.__externalRCRef())
            }
        }
        public init(
            size: Swift.Int32
        ) {
            fatalError()
        }
        public init(
            size: Swift.Int32,
            `init`: @escaping (Swift.Int32) -> Swift.Int32
        ) {
            fatalError()
        }
        package override init(
            __externalRCRef: Swift.UInt
        ) {
            super.init(__externalRCRef: __externalRCRef)
        }
    }
}
