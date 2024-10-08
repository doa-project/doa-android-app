package com.example.doa_app.data.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.doa_app.utils.ImageUtils
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.URL

class FirebaseStorageManager(
    private val imageUtils : ImageUtils
) {
    suspend fun uploadImage( bitmap: Bitmap): String? = withContext(Dispatchers.IO) {
        val byteArrayOutputStream = ByteArrayOutputStream().apply {
            val bitmapToApi = imageUtils.cropToSquare(bitmap)
            bitmapToApi.compress(Bitmap.CompressFormat.JPEG, 60, this)
        }
        val dataBytes = byteArrayOutputStream.toByteArray()

        val storage = FirebaseStorage.getInstance()
        val ref = storage.getReference("gallery").child("img_of_doa_campaign_${System.currentTimeMillis()}.jpg")

        return@withContext try {
            ref.putBytes(dataBytes).await()
            val downloadUrl = ref.downloadUrl.await().toString()
            Log.d("FirebaseStorageManager", "Image uploaded successfully")
            downloadUrl
        } catch (e: Exception) {
            Log.d("FirebaseStorageManager", "Failed to upload image: ${e.message}")
            null
        }
    }
    suspend fun loadImageFromFirebase(imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        return@withContext try {
            val inputStream = URL(imageUrl).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: Exception) {
            Log.d("FirebaseStorageManager", "Failed to load image: ${e.message}")
            null
        }
    }
}
