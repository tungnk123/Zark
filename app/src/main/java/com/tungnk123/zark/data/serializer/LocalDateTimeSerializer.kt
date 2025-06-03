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
import java.time.format.DateTimeParseException

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        val instant = value.toInstant(ZoneOffset.UTC)
        val formatted = DateTimeFormatter.ISO_INSTANT.format(instant)
        encoder.encodeString(formatted)
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val rawString = decoder.decodeString()

        try {
            val instant = java.time.Instant.parse(rawString)
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        } catch (e: DateTimeParseException) {
            e.printException()
            throw IllegalArgumentException("Unsupported date format: $rawString")
        }
    }
}
