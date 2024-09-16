package com.example.doa_app.utils

import com.example.doa_app.data.model.api.StringImage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ImageOrStringDeserializer : JsonDeserializer<List<StringImage>> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<StringImage> {
            val images = mutableListOf<StringImage>()
            if (json.isJsonArray) {
                for (element in json.asJsonArray) {
                    if (element.isJsonObject) {
                        images.add(context.deserialize(element, StringImage::class.java))
                    } else if (element.isJsonPrimitive && element.asJsonPrimitive.isString) {
                        images.add(StringImage(element.asString))
                    }
                }
            }
            return images
        }
    }

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<List<StringImage>>() {}.type, ImageOrStringDeserializer())
        .create()
