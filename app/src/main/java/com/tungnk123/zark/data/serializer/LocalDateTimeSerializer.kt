package com.tungnk123.zark.data.serializer

import com.tungnk123.zark.utils.extensions.printException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val utc = value.atOffset(ZoneOffset.UTC)
            .format(formatter)
        encoder.encodeString(utc)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val string = decoder.decodeString().removeSuffix("Z")

        val patterns = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SS",
            "yyyy-MM-dd'T'HH:mm:ss.S",
            "yyyy-MM-dd'T'HH:mm:ss"
        )

        for (pattern in patterns) {
            try {
                return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(pattern))
            } catch (e: Exception) {
                e.printException()
            }
        }

        throw IllegalArgumentException("Unsupported date format: $string")
    }


}
