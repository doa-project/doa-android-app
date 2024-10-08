package com.example.doa_app.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageUtils {
    fun cropToSquare(bitmap: Bitmap): Bitmap {
        val dimension = bitmap.width.coerceAtMost(bitmap.height)
        val width = (bitmap.width - dimension) / 2
        val height = (bitmap.height - dimension) / 2
        return Bitmap.createBitmap(bitmap, width, height, dimension, dimension)
    }

}