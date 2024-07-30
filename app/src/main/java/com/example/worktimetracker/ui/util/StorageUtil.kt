package com.example.worktimetracker.ui.util

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class StorageUtil {

    companion object {
        suspend fun uploadAvatarToStorage(
            uri: Uri,
            context: Context,
            id: Int
        ): String? {
            val storage = Firebase.storage
            val storageRef = storage.reference
            val spaceRef = storageRef.child("avatars/$id/avatar.jpg")

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            return byteArray?.let {
                try {
                    // Tải lên tệp
                    val uploadTask = spaceRef.putBytes(byteArray).await()

                    // Lấy URL của tệp đã tải lên
                    val downloadUrl = spaceRef.downloadUrl.await().toString()
                    downloadUrl
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

}