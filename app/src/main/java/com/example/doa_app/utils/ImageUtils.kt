package com.example.doa_app.utils
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.GZIPOutputStream

class ImageUtils {
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val squareBitmap = cropToSquare(bitmap)
        squareBitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToBitmap(base64Str: String): Bitmap? {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun uriToBitmap(context: Context, imageUri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream)
    }

//    fun getRotatedBitmap(context: Context, imageUri: Uri): Bitmap {
//        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
//        val rotatedBitmap: Bitmap
//
//        try {
//            val inputStream = context.contentResolver.openInputStream(imageUri)
//            val exif = ExifInterface(inputStream!!)
//            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
//            rotatedBitmap = when (orientation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)
//                ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)
//                ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)
//                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipImage(bitmap, true, false)
//                ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipImage(bitmap, false, true)
//                else -> bitmap
//            }
//        } catch (e: IOException) {
//            Log.e("getRotatedBitmap", "Failed to get rotated bitmap", e)
//            return cropToSquare(bitmap)
//        }
//
//        return cropToSquare(rotatedBitmap)
//    }

//    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
//        val matrix = Matrix()
//        matrix.postRotate(angle)
//        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
//    }
//
//    private fun flipImage(source: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
//        val matrix = Matrix()
//        matrix.postScale(
//            if (horizontal) -1f else 1f,
//            if (vertical) -1f else 1f,
//            source.width / 2f,
//            source.height / 2f
//        )
//        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
//    }
    private fun cropToSquare(bitmap: Bitmap): Bitmap {
        val dimension = Math.min(bitmap.width, bitmap.height)
        val width = (bitmap.width - dimension) / 2
        val height = (bitmap.height - dimension) / 2
        return Bitmap.createBitmap(bitmap, width, height, dimension, dimension)
    }

//    private fun compressBase64(base64Str: String): String {
//        return try {
//            val byteArrayOutputStream = ByteArrayOutputStream()
//            GZIPOutputStream(byteArrayOutputStream).use { gzip ->
//                gzip.write(base64Str.toByteArray(Charsets.UTF_8))
//            }
//            Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            base64Str
//        }
//    }
}