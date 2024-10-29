package com.izowooi.observer.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.izowooi.observer.model.ServerStatus

@Composable
fun ServerStatusRow(serverStatus: ServerStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = serverStatus.icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = serverStatus.statusName)
            Text(text = serverStatus.serverName)
            Text(text = serverStatus.checkTime)
        }
    }
}

@Composable
fun ServerStatusList(serverStatuses: List<ServerStatus>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        serverStatuses.forEach { serverStatus ->
            ServerStatusRow(serverStatus = serverStatus)
        }
    }
}