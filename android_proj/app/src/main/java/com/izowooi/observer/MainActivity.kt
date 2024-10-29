package com.izowooi.observer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.izowooi.observer.model.ServerStatus
import com.izowooi.observer.ui.ServerStatusList
import com.izowooi.observer.ui.theme.ObserverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObserverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                    ServerStatusList(
                        serverStatuses = serverStatuses,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
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