package com.izowooi.observer.util

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

object FirebaseUtil {
    private const val DATABASE_URL = "https://cake-watch-default-rtdb.asia-southeast1.firebasedatabase.app"

    fun initialize(context: Context) {
        if (FirebaseApp.getApps(context).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setDatabaseUrl(DATABASE_URL)
                .setApplicationId("com.izowooi.observer")
                .build()
            FirebaseApp.initializeApp(context, options)
        }
    }

    suspend fun getStatus(serviceName: String): Boolean {
        val database = FirebaseDatabase.getInstance(DATABASE_URL)
        val ref = database.getReference("service_status/$serviceName/status")
        val snapshot = ref.get().await()
        return snapshot.getValue(Boolean::class.java) ?: false
    }
}