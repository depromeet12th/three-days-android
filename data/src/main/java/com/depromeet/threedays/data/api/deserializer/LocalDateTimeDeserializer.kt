package com.depromeet.threedays.data.api.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        return json?.let {
            LocalDateTime.parse(
                it.asString,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            )
        }
    }
}
