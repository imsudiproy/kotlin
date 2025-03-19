@_exported import ExportedKotlinPackages
import KotlinRuntimeSupport
import KotlinRuntime
import stdlib

public typealias `internal` = ExportedKotlinPackages.kotlinx.serialization.`internal`
public typealias modules = ExportedKotlinPackages.kotlinx.serialization.modules
public typealias descriptors = ExportedKotlinPackages.kotlinx.serialization.descriptors
public typealias builtins = ExportedKotlinPackages.kotlinx.serialization.builtins
public func serializer(
    kClass: Swift.Never,
    typeArgumentsSerializers: [Swift.Never],
    isNullable: Swift.Bool
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializer(kClass: kClass, typeArgumentsSerializers: typeArgumentsSerializers, isNullable: isNullable)
}
public func serializer(
    type: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializer(type: type)
}
public func serializer(
    _ receiver: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializer(receiver)
}
public func serializer(
    _ receiver: Swift.Never,
    kClass: Swift.Never,
    typeArgumentsSerializers: [Swift.Never],
    isNullable: Swift.Bool
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializer(receiver, kClass: kClass, typeArgumentsSerializers: typeArgumentsSerializers, isNullable: isNullable)
}
public func serializer(
    _ receiver: Swift.Never,
    type: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializer(receiver, type: type)
}
public func serializerOrNull(
    type: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializerOrNull(type: type)
}
public func serializerOrNull(
    _ receiver: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializerOrNull(receiver)
}
public func serializerOrNull(
    _ receiver: Swift.Never,
    type: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.serializerOrNull(receiver, type: type)
}
public func decodeFromHexString(
    _ receiver: Swift.Never,
    deserializer: Swift.Never,
    hex: Swift.String
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.decodeFromHexString(receiver, deserializer: deserializer, hex: hex)
}
public func encodeToHexString(
    _ receiver: Swift.Never,
    serializer: Swift.Never,
    value: Swift.Never
) -> Swift.String {
    ExportedKotlinPackages.kotlinx.serialization.encodeToHexString(receiver, serializer: serializer, value: value)
}
public func findPolymorphicSerializer(
    _ receiver: Swift.Never,
    decoder: Swift.Never,
    klassName: Swift.String?
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.findPolymorphicSerializer(receiver, decoder: decoder, klassName: klassName)
}
public func findPolymorphicSerializer(
    _ receiver: Swift.Never,
    encoder: Swift.Never,
    value: Swift.Never
) -> Swift.Never {
    ExportedKotlinPackages.kotlinx.serialization.findPolymorphicSerializer(receiver, encoder: encoder, value: value)
}
public extension ExportedKotlinPackages.kotlinx.serialization {
    public static func serializer(
        kClass: Swift.Never,
        typeArgumentsSerializers: [Swift.Never],
        isNullable: Swift.Bool
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        type: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: Swift.Never,
        kClass: Swift.Never,
        typeArgumentsSerializers: [Swift.Never],
        isNullable: Swift.Bool
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: Swift.Never,
        type: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializerOrNull(
        type: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializerOrNull(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializerOrNull(
        _ receiver: Swift.Never,
        type: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func decodeFromHexString(
        _ receiver: Swift.Never,
        deserializer: Swift.Never,
        hex: Swift.String
    ) -> Swift.Never {
        fatalError()
    }
    public static func encodeToHexString(
        _ receiver: Swift.Never,
        serializer: Swift.Never,
        value: Swift.Never
    ) -> Swift.String {
        fatalError()
    }
    public static func findPolymorphicSerializer(
        _ receiver: Swift.Never,
        decoder: Swift.Never,
        klassName: Swift.String?
    ) -> Swift.Never {
        fatalError()
    }
    public static func findPolymorphicSerializer(
        _ receiver: Swift.Never,
        encoder: Swift.Never,
        value: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
}
public extension ExportedKotlinPackages.kotlinx.serialization.`internal` {
    public static func InlinePrimitiveDescriptor(
        name: Swift.String,
        primitiveSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func throwArrayMissingFieldException(
        seenArray: ExportedKotlinPackages.kotlin.IntArray,
        goldenMaskArray: ExportedKotlinPackages.kotlin.IntArray,
        descriptor: Swift.Never
    ) -> Swift.Void {
        fatalError()
    }
    public static func throwMissingFieldException(
        seen: Swift.Int32,
        goldenMask: Swift.Int32,
        descriptor: Swift.Never
    ) -> Swift.Void {
        fatalError()
    }
    public static func jsonCachedSerialNames(
        _ receiver: Swift.Never
    ) -> Swift.Set<Swift.String> {
        fatalError()
    }
}
public extension ExportedKotlinPackages.kotlinx.serialization.modules {
    @available(*, deprecated, message: "Deprecated in the favour of 'EmptySerializersModule()'. Replacement: EmptySerializersModule()")
    public static var EmptySerializersModule: Swift.Never {
        get {
            fatalError()
        }
    }
    public static func EmptySerializersModule() -> Swift.Never {
        fatalError()
    }
    public static func serializersModuleOf(
        kClass: Swift.Never,
        serializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func overwriteWith(
        _ receiver: Swift.Never,
        other: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
}
public extension ExportedKotlinPackages.kotlinx.serialization.descriptors {
    public static func getCapturedKClass(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func getElementDescriptors(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func getElementNames(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func getNullable(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func PrimitiveSerialDescriptor(
        serialName: Swift.String,
        kind: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serialDescriptor(
        serialName: Swift.String,
        original: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func buildClassSerialDescriptor(
        serialName: Swift.String,
        typeParameters: Swift.Never,
        builderAction: @escaping () -> Swift.Void
    ) -> Swift.Never {
        fatalError()
    }
    public static func buildSerialDescriptor(
        serialName: Swift.String,
        kind: Swift.Never,
        typeParameters: Swift.Never,
        builder: @escaping () -> Swift.Void
    ) -> Swift.Never {
        fatalError()
    }
    public static func listSerialDescriptor(
        elementDescriptor: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func mapSerialDescriptor(
        keyDescriptor: Swift.Never,
        valueDescriptor: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func serialDescriptor(
        type: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func setSerialDescriptor(
        elementDescriptor: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func getContextualDescriptor(
        _ receiver: Swift.Never,
        descriptor: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func getPolymorphicDescriptors(
        _ receiver: Swift.Never,
        descriptor: Swift.Never
    ) -> [Swift.Never] {
        fatalError()
    }
}
public extension ExportedKotlinPackages.kotlinx.serialization.builtins {
    public static func getNullable(
        _ receiver: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func ArraySerializer(
        kClass: Swift.Never,
        elementSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func BooleanArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func ByteArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func CharArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func DoubleArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func FloatArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func IntArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func ListSerializer(
        elementSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func LongArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func MapEntrySerializer(
        keySerializer: Swift.Never,
        valueSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func MapSerializer(
        keySerializer: Swift.Never,
        valueSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func NothingSerializer() -> Swift.Never {
        fatalError()
    }
    public static func PairSerializer(
        keySerializer: Swift.Never,
        valueSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func SetSerializer(
        elementSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func ShortArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func TripleSerializer(
        aSerializer: Swift.Never,
        bSerializer: Swift.Never,
        cSerializer: Swift.Never
    ) -> Swift.Never {
        fatalError()
    }
    public static func UByteArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func UIntArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func ULongArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func UShortArraySerializer() -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Boolean.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Byte.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Char.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Double.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Float.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Int.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Long.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.Short.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.String.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.UByte.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.UInt.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.ULong.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.UShort.Companion
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: Swift.Void
    ) -> Swift.Never {
        fatalError()
    }
    public static func serializer(
        _ receiver: ExportedKotlinPackages.kotlin.time.Duration.Companion
    ) -> Swift.Never {
        fatalError()
    }
}
