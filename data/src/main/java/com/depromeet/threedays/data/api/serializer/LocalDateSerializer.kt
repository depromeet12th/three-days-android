package com.depromeet.threedays.data.api.serializer

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateSerializer : JsonSerializer<LocalDate> {
    override fun serialize(
        src: LocalDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return src?.let { JsonPrimitive(it.format(DateTimeFormatter.ISO_LOCAL_DATE)) }
            ?: JsonNull.INSTANCE
    }
}
