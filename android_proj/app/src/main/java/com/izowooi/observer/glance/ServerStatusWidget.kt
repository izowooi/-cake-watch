package com.izowooi.observer.glance

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.text.Text
import com.izowooi.observer.util.FirebaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServerStatusWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val statusUS = getStatusAsync("JenkinsUS")
        val statusJP = getStatusAsync("JenkinsJP")
        val statusKR = getStatusAsync("JenkinsKR")
        val statusMachine = getStatusAsync("Machine")
        Log.d("ServerStatusWidget", "statusUS: $statusUS, statusJP: $statusJP, statusKR: $statusKR, statusMachine: $statusMachine")
        provideContent {
            Column(
                modifier = GlanceModifier.fillMaxWidth()
            ) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth()
                ) {
                    //Text(statusUS)
                    Text("hello")
                    Text(statusJP)
                    Text(statusKR)
                    Text(statusMachine)
                }
                Text("Time")
            }
        }
    }

    private suspend fun getStatusAsync(serviceName: String): String {
        return withContext(Dispatchers.IO) {
            val status = FirebaseUtil.getStatus(serviceName)
            if (status) "1" else "0"
        }
    }
}