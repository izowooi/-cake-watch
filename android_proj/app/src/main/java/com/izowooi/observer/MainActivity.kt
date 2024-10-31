package com.izowooi.observer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.izowooi.observer.model.ServerStatus
import com.izowooi.observer.ui.ServerStatusList
import com.izowooi.observer.ui.theme.ObserverTheme
import com.izowooi.observer.util.FirebaseUtil
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseUtil.initialize(this)

        setContent {
            ObserverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var serverStatuses by remember {
                        mutableStateOf(
                            listOf(
                                ServerStatus(
                                    icon = Icons.Default.Add,
                                    statusName = "None",
                                    serverName = "JenkinsUS",
                                    checkTime = "12:00 PM"
                                ),
                                ServerStatus(
                                    icon = Icons.Default.Add,
                                    statusName = "None",
                                    serverName = "JenkinsJP",
                                    checkTime = "12:00 PM"
                                ),
                                ServerStatus(
                                    icon = Icons.Default.Add,
                                    statusName = "None",
                                    serverName = "JenkinsKR",
                                    checkTime = "12:00 PM"
                                ),
                                ServerStatus(
                                    icon = Icons.Default.Done,
                                    statusName = "None",
                                    serverName = "Machine",
                                    checkTime = "12:05 PM"
                                )
                            )
                        )
                    }
                    val coroutineScope = rememberCoroutineScope()

                    // Define the update task
                    updateRunnable = Runnable {
                        coroutineScope.launch {
                            Log.d("MainActivity", "Updating status")
                            val statusUS = FirebaseUtil.getStatus("JenkinsUS")
                            val statusJP = FirebaseUtil.getStatus("JenkinsJP")
                            val statusKR = FirebaseUtil.getStatus("JenkinsKR")
                            val statusMachine = FirebaseUtil.getStatus("Machine")
                            Log.d("MainActivity", "statusUS: $statusUS, statusJP: $statusJP, statusKR: $statusKR, statusMachine: $statusMachine")

                            // Update the status of Server 1 based on the fetched status
                            serverStatuses = serverStatuses.map { serverStatus ->
                                when (serverStatus.serverName) {
                                    "JenkinsUS" -> serverStatus.copy(statusName = if (statusUS) "Online" else "Offline")
                                    "JenkinsJP" -> serverStatus.copy(statusName = if (statusJP) "Online" else "Offline")
                                    "JenkinsKR" -> serverStatus.copy(statusName = if (statusKR) "Online" else "Offline")
                                    "Machine" -> serverStatus.copy(statusName = if (statusMachine) "Online" else "Offline")
                                    else -> serverStatus
                                }
                            }
                        }
                        // Schedule the next update
                        //handler.postDelayed(updateRunnable, 60000) // 60 seconds
                        handler.postDelayed(updateRunnable, 10000) // 10 seconds
                    }

                    // Start the update task
                    handler.post(updateRunnable)

                    Column(modifier = Modifier.padding(innerPadding)) {
                        ServerStatusList(
                            serverStatuses = serverStatuses,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    Log.d("MainActivity", "Clicked")
                                    val statusUS = FirebaseUtil.getStatus("JenkinsUS")
                                    val statusJP = FirebaseUtil.getStatus("JenkinsJP")
                                    val statusKR = FirebaseUtil.getStatus("JenkinsKR")
                                    val statusMachine = FirebaseUtil.getStatus("Machine")
                                    Log.d("MainActivity", "statusUS: $statusUS, statusJP: $statusJP, statusKR: $statusKR, statusMachine: $statusMachine")

                                    // Update the status of Server 1 based on the fetched status
                                    serverStatuses = serverStatuses.map { serverStatus ->

                                        if (serverStatus.serverName == "statusUS") {
                                            serverStatus.copy(statusName = if (statusUS) "Online" else "Offline")
                                        }
                                        else {
                                            serverStatus
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Check Status")
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Resume the update task
        //handler.post(updateRunnable)
    }

    override fun onPause() {
        super.onPause()
        // Stop the update task
        //handler.removeCallbacks(updateRunnable)
    }
}

@Preview(showBackground = true)
@Composable
fun ServerStatusListPreview() {
    ObserverTheme {
        val serverStatuses = listOf(
            ServerStatus(
                icon = Icons.Default.Add,
                statusName = "Online",
                serverName = "Server 1",
                checkTime = "12:00 PM"
            ),
            ServerStatus(
                icon = Icons.Default.Done,
                statusName = "Offline",
                serverName = "Server 2",
                checkTime = "12:05 PM"
            )
        )
        ServerStatusList(serverStatuses = serverStatuses)
    }
}