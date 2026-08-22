package com.example.data.model

data class ChannelItem(
    val id: String,
    val displayName: String,
    val serverUrl: String = "https://cytu.be",
    val roomName: String,
    val description: String = "",
    val badgeColorHex: String = "#8A2BE2",
    val isCustom: Boolean = false
)
