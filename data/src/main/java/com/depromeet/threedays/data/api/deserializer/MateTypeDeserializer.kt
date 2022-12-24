package com.depromeet.threedays.data.api.deserializer

import com.depromeet.threedays.domain.entity.mate.MateType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MateTypeDeserializer : JsonDeserializer<MateType> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MateType? {
        return json?.let {
            getMateType(it.asString)
        }
    }

    private fun getMateType(type: String): MateType {
        return if(type == "WHIP") {
            MateType.WHIP
        } else if(type == "CARROT") {
            MateType.CARROT
        } else {
            throw IllegalArgumentException("MateType type unknown.")
        }
    }
}
