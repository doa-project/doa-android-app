package com.example.doa_app.data.firebase

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.URL

class FirebaseStorageManager {

    // Upload image to Firebase and return the image URL
    suspend fun uploadImage(bitmap: Bitmap): String? = withContext(Dispatchers.IO) {
        val byteArrayOutputStream = ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, this)
        }
        val dataBytes = byteArrayOutputStream.toByteArray()

        val storage = FirebaseStorage.getInstance()
        val ref = storage.getReference("gallery").child("img_of_camera_template_${System.currentTimeMillis()}.jpg")

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

    // Load image from Firebase Storage URL and return Bitmap
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
