package com.example.doa_app.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream

class ImageUtils {
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val squareBitmap = cropToSquare(bitmap)
        squareBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToBitmap(base64Str: String): Bitmap? {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
    private fun cropToSquare(bitmap: Bitmap): Bitmap {
        val dimension = bitmap.width.coerceAtMost(bitmap.height)
        val width = (bitmap.width - dimension) / 2
        val height = (bitmap.height - dimension) / 2
        return Bitmap.createBitmap(bitmap, width, height, dimension, dimension)
    }

}