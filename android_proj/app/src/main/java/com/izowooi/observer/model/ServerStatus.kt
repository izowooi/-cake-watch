package com.izowooi.observer.model
import androidx.compose.ui.graphics.vector.ImageVector

data class ServerStatus(
    val icon: ImageVector,
    val statusName: String,
    val serverName: String,
    val checkTime: String
)

