/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.types

import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.types.model.*
import org.jetbrains.kotlin.utils.addToStdlib.foldMap

// We assume type IS an invariant type projection to prevent additional wrapper here
// (more exactly, invariant type projection contains type)
sealed class ConeKotlinType(
    val typeArguments: Array<out ConeTypeProjection>,
    val attributes: ConeAttributes
) : ConeKotlinTypeProjection(), KotlinTypeMarker, TypeArgumentListMarker {
    final override val kind: ProjectionKind
        get() = ProjectionKind.INVARIANT

    final override val type: ConeKotlinType
        get() = this

    abstract val nullability: ConeNullability

    final override fun toString(): String {
        return renderForDebugging()
    }

    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}

sealed class ConeSimpleKotlinType(typeArguments: Array<out ConeTypeProjection>, attributes: ConeAttributes) :
    ConeKotlinType(typeArguments, attributes), SimpleTypeMarker

class ConeClassLikeErrorLookupTag(override val classId: ClassId) : ConeClassLikeLookupTag()

class ConeErrorType(
    val diagnostic: ConeDiagnostic,
    val isUninferredParameter: Boolean = false,
    typeArguments: Array<out ConeTypeProjection> = EMPTY_ARRAY,
    attributes: ConeAttributes = ConeAttributes.Empty
) : ConeClassLikeType(typeArguments, attributes) {
    override val lookupTag: ConeClassLikeLookupTag
        get() = ConeClassLikeErrorLookupTag(ClassId.fromString("<error>"))

    override val nullability: ConeNullability
        get() = ConeNullability.UNKNOWN

    override fun equals(other: Any?) = this === other
    override fun hashCode(): Int = System.identityHashCode(this)
}

abstract class ConeLookupTagBasedType(
    typeArguments: Array<out ConeTypeProjection>,
    attributes: ConeAttributes
) : ConeSimpleKotlinType(typeArguments, attributes) {
    abstract val lookupTag: ConeClassifierLookupTag
}

abstract class ConeClassLikeType(
    typeArguments: Array<out ConeTypeProjection>,
    attributes: ConeAttributes
) : ConeLookupTagBasedType(typeArguments, attributes) {
    abstract override val lookupTag: ConeClassLikeLookupTag
}

open class ConeFlexibleType(
    val lowerBound: ConeSimpleKotlinType,
    val upperBound: ConeSimpleKotlinType,
    typeArguments: Array<out ConeTypeProjection> = lowerBound.typeArguments,
    attributes: ConeAttributes = lowerBound.attributes
) : ConeKotlinType(typeArguments, attributes), FlexibleTypeMarker {
    final override val nullability: ConeNullability
        get() = lowerBound.nullability.takeIf { it == upperBound.nullability } ?: ConeNullability.UNKNOWN

    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        // I suppose dynamic type (see below) and flexible type should use the same equals,
        // because ft<Any?, Nothing> should never be created
        if (other !is ConeFlexibleType) return false

        if (lowerBound != other.lowerBound) return false
        if (upperBound != other.upperBound) return false

        return true
    }

    final override fun hashCode(): Int {
        var result = lowerBound.hashCode()
        result = 31 * result + upperBound.hashCode()
        return result
    }
}

@RequiresOptIn(message = "Please use ConeDynamicType.create instead")
annotation class DynamicTypeConstructor

class ConeDynamicType @DynamicTypeConstructor constructor(
    lowerBound: ConeSimpleKotlinType,
    upperBound: ConeSimpleKotlinType
) : ConeFlexibleType(lowerBound, upperBound), DynamicTypeMarker {
    companion object
}

fun ConeSimpleKotlinType.unwrapDefinitelyNotNull(): ConeSimpleKotlinType {
    return when (this) {
        is ConeDefinitelyNotNullType -> original
        else -> this
    }
}

class ConeCapturedTypeConstructor(
    val projection: ConeTypeProjection,
    var supertypes: List<ConeKotlinType>? = null,
    val typeParameterMarker: TypeParameterMarker? = null
) : CapturedTypeConstructorMarker

data class ConeCapturedType(
    val captureStatus: CaptureStatus,
    val lowerType: ConeKotlinType?,
    override val nullability: ConeNullability = ConeNullability.NOT_NULL,
    val constructor: ConeCapturedTypeConstructor,
    val attrs: ConeAttributes = ConeAttributes.Empty,
    val isProjectionNotNull: Boolean = false
) : ConeSimpleKotlinType(emptyArray(), attrs), CapturedTypeMarker {
    constructor(
        captureStatus: CaptureStatus, lowerType: ConeKotlinType?, projection: ConeTypeProjection,
        typeParameterMarker: TypeParameterMarker
    ) : this(
        captureStatus,
        lowerType,
        constructor = ConeCapturedTypeConstructor(
            projection,
            typeParameterMarker = typeParameterMarker
        )
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConeCapturedType

        if (lowerType != other.lowerType) return false
        if (constructor != other.constructor) return false
        if (captureStatus != other.captureStatus) return false
        if (nullability != other.nullability) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 7
        result = 31 * result + (lowerType?.hashCode() ?: 0)
        result = 31 * result + constructor.hashCode()
        result = 31 * result + captureStatus.hashCode()
        result = 31 * result + nullability.hashCode()
        return result
    }
}


data class ConeDefinitelyNotNullType(val original: ConeSimpleKotlinType) :
    ConeSimpleKotlinType(original.typeArguments, original.attributes), DefinitelyNotNullTypeMarker {

    override val nullability: ConeNullability
        get() = ConeNullability.NOT_NULL

    companion object
}

class ConeRawType private constructor(
    lowerBound: ConeSimpleKotlinType,
    upperBound: ConeSimpleKotlinType
) : ConeFlexibleType(lowerBound, upperBound) {
    companion object {
        fun create(
            lowerBound: ConeSimpleKotlinType,
            upperBound: ConeSimpleKotlinType,
        ): ConeRawType {
            require(lowerBound is ConeClassLikeType && upperBound is ConeClassLikeType) {
                "Raw bounds are expected to be class-like types, but $lowerBound and $upperBound were found"
            }

            val lowerBoundToUse = if (!lowerBound.attributes.contains(CompilerConeAttributes.RawType)) {
                ConeClassLikeTypeImpl(
                    lowerBound.lookupTag, lowerBound.typeArguments, lowerBound.isNullable,
                    lowerBound.attributes + CompilerConeAttributes.RawType
                )
            } else {
                lowerBound
            }

            return ConeRawType(lowerBoundToUse, upperBound)
        }
    }
}

/**
 * Contract of the intersection type: it is flat. It means that an intersection type can not contain another intersection type inside it.
 * To comply with this contract, construct new intersection types only via [org.jetbrains.kotlin.fir.types.ConeTypeIntersector].
 */
class ConeIntersectionType(
    val intersectedTypes: Collection<ConeKotlinType>,
    val alternativeType: ConeKotlinType? = null,
) : ConeSimpleKotlinType(emptyArray(), getAttributes(intersectedTypes)), IntersectionTypeConstructorMarker {
    override val nullability: ConeNullability
        get() = ConeNullability.NOT_NULL

    private var hashCode = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConeIntersectionType

        if (intersectedTypes != other.intersectedTypes) return false

        return true
    }

    override fun hashCode(): Int {
        if (hashCode != 0) return hashCode
        return intersectedTypes.hashCode().also { hashCode = it }
    }

    companion object {
        private fun getAttributes(intersectedTypes: Collection<ConeKotlinType>): ConeAttributes {
            return intersectedTypes.foldMap(
                { it.attributes },
                { a, b -> a.intersect(b) }
            )
        }
    }
}
