package com.example.worktimetracker.data.remote.adapters

import com.example.worktimetracker.data.remote.enums.Priority
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class PriorityAdapter : JsonDeserializer<Priority>, JsonSerializer<Priority> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?,
    ): Priority {
        return Priority.valueOf(json.asString)
    }

    override fun serialize(
        src: Priority,
        typeOfSrc: Type,
        context: JsonSerializationContext?,
    ): JsonElement? {
        return JsonPrimitive(src.name)
    }
}