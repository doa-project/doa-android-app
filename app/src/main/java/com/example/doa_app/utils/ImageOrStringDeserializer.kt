package com.example.doa_app.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ImageOrStringDeserializer : JsonDeserializer<List<Image>> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<Image> {
            val images = mutableListOf<Image>()
            if (json.isJsonArray) {
                for (element in json.asJsonArray) {
                    if (element.isJsonObject) {
                        images.add(context.deserialize(element, Image::class.java))
                    } else if (element.isJsonPrimitive && element.asJsonPrimitive.isString) {
                        images.add(Image(element.asString))
                    }
                }
            }
            return images
        }
    }

    data class Image(
        val image: String // Or other fields if your Image class has more fields
    )

    val gson: Gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<List<Image>>() {}.type, ImageOrStringDeserializer())
        .create()
