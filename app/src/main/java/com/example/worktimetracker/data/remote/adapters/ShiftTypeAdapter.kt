package com.example.worktimetracker.data.remote.adapters

import com.example.worktimetracker.data.remote.response.ShiftType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class ShiftTypeAdapter : JsonSerializer<ShiftType>, JsonDeserializer<ShiftType> {
    override fun serialize(
        src: ShiftType,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement? {
        return JsonPrimitive(src.name)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): ShiftType? {
        return ShiftType.valueOf(json.asString)
    }
}