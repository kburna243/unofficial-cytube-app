package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.ChannelItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject

class ChannelRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("mfcytube_channels_prefs", Context.MODE_PRIVATE)

    val defaultChannels = listOf(
        ChannelItem(
            id = "Channel-Z",
            displayName = "Channel-Z",
            serverUrl = "https://cytu.be",
            roomName = "Channel-Z",
            description = "Cult Cinema, Trash, Sci-Fi & Retro B-Movies",
            badgeColorHex = "#E040FB"
        ),
        ChannelItem(
            id = "420Grindhouse",
            displayName = "420 Grindhouse",
            serverUrl = "https://cytu.be",
            roomName = "420Grindhouse",
            description = "B-Movies, Cult Cinema, Exploitation & Kung-Fu",
            badgeColorHex = "#00E676"
        ),
        ChannelItem(
            id = "The-Kinoplex",
            displayName = "The Kinoplex",
            serverUrl = "https://cytu.be",
            roomName = "The-Kinoplex",
            description = "Cinema, Movie Marathons & Live Kino",
            badgeColorHex = "#FF5722"
        ),
        ChannelItem(
            id = "spookymovienight",
            displayName = "Spooky Movie Night",
            serverUrl = "https://cytu.be",
            roomName = "spookymovienight",
            description = "Spooky Horror, Cult Movies, Music & More",
            badgeColorHex = "#AB47BC"
        ),
        ChannelItem(
            id = "sneedtv",
            displayName = "Sneed TV",
            serverUrl = "https://cytu.be",
            roomName = "sneedtv",
            description = "Memorial Kino, Feature Films & Specials",
            badgeColorHex = "#FFCA28"
        ),
        ChannelItem(
            id = "v4c",
            displayName = "vidya4chan",
            serverUrl = "https://cytu.be",
            roomName = "v4c",
            description = "Video games, movies, anime & memes",
            badgeColorHex = "#66BB6A"
        ),
        ChannelItem(
            id = "American-Dad",
            displayName = "American Dad",
            serverUrl = "https://cytu.be",
            roomName = "American-Dad",
            description = "American Dad 24/7 Series Stream",
            badgeColorHex = "#42A5F5"
        ),
        ChannelItem(
            id = "spookyvision",
            displayName = "Spooky Vision",
            serverUrl = "https://cytu.be",
            roomName = "spookyvision",
            description = "Cozy Horror, Games, Community & Fun",
            badgeColorHex = "#7E57C2"
        ),
        ChannelItem(
            id = "always_always_sunny",
            displayName = "Always Sunny",
            serverUrl = "https://cytu.be",
            roomName = "always_always_sunny",
            description = "It's Always Sunny In Philadelphia 24/7 Stream",
            badgeColorHex = "#FFA726"
        ),
        ChannelItem(
            id = "afterparty",
            displayName = "Afterparty",
            serverUrl = "https://cytu.be",
            roomName = "afterparty",
            description = "Marekissers Anonymous & Classic Vault Specials",
            badgeColorHex = "#EC407A"
        ),
        ChannelItem(
            id = "South-Park-Show",
            displayName = "South Park",
            serverUrl = "https://cytu.be",
            roomName = "South-Park-Show",
            description = "South Park Uncensored 24/7 Episodes",
            badgeColorHex = "#26C6DA"
        )
    )

    private val _channels = MutableStateFlow(loadChannels())
    val channels: StateFlow<List<ChannelItem>> = _channels.asStateFlow()

    private val _selectedChannel = MutableStateFlow(loadSelectedChannel())
    val selectedChannel: StateFlow<ChannelItem> = _selectedChannel.asStateFlow()

    private fun loadChannels(): List<ChannelItem> {
        val jsonString = prefs.getString("custom_channels_json", null) ?: return defaultChannels
        return try {
            val list = mutableListOf<ChannelItem>()
            list.addAll(defaultChannels)
            val array = JSONArray(jsonString)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val room = obj.getString("roomName")
                // Avoid duplicating default channels
                if (defaultChannels.none { it.roomName.equals(room, ignoreCase = true) }) {
                    list.add(
                        ChannelItem(
                            id = obj.getString("id"),
                            displayName = obj.getString("displayName"),
                            serverUrl = obj.optString("serverUrl", "https://cytu.be"),
                            roomName = room,
                            description = obj.optString("description", "Custom CyTube Room"),
                            badgeColorHex = obj.optString("badgeColorHex", "#3DDC84"),
                            isCustom = true
                        )
                    )
                }
            }
            list
        } catch (e: Exception) {
            defaultChannels
        }
    }

    private fun loadSelectedChannel(): ChannelItem {
        val selectedRoom = prefs.getString("selected_room", "Channel-Z") ?: "Channel-Z"
        return _channels.value.find { it.roomName.equals(selectedRoom, ignoreCase = true) } ?: _channels.value.first()
    }

    fun selectChannel(channel: ChannelItem) {
        prefs.edit().putString("selected_room", channel.roomName).apply()
        _selectedChannel.value = channel
    }

    fun selectNextChannel(): ChannelItem {
        val list = _channels.value
        if (list.isEmpty()) return _selectedChannel.value
        val currentIndex = list.indexOfFirst { it.roomName == _selectedChannel.value.roomName }
        val nextIndex = if (currentIndex < 0 || currentIndex + 1 >= list.size) 0 else currentIndex + 1
        val nextChannel = list[nextIndex]
        selectChannel(nextChannel)
        return nextChannel
    }

    fun selectPreviousChannel(): ChannelItem {
        val list = _channels.value
        if (list.isEmpty()) return _selectedChannel.value
        val currentIndex = list.indexOfFirst { it.roomName == _selectedChannel.value.roomName }
        val prevIndex = if (currentIndex <= 0) list.size - 1 else currentIndex - 1
        val prevChannel = list[prevIndex]
        selectChannel(prevChannel)
        return prevChannel
    }

    fun addCustomChannel(displayName: String, roomName: String, serverUrl: String = "https://cytu.be") {
        val cleanRoom = roomName.trim().replace("https://cytu.be/r/", "").replace("cytu.be/r/", "")
        if (cleanRoom.isBlank()) return
        val newChan = ChannelItem(
            id = cleanRoom,
            displayName = displayName.ifBlank { cleanRoom },
            serverUrl = serverUrl,
            roomName = cleanRoom,
            description = "Custom Room (cytu.be/r/$cleanRoom)",
            badgeColorHex = "#3DDC84",
            isCustom = true
        )
        val current = _channels.value.toMutableList()
        if (current.none { it.roomName.equals(cleanRoom, ignoreCase = true) }) {
            current.add(newChan)
            saveCustomChannels(current.filter { it.isCustom })
            _channels.value = current
        }
    }

    fun deleteCustomChannel(channel: ChannelItem) {
        if (!channel.isCustom) return
        val current = _channels.value.filterNot { it.id == channel.id }
        saveCustomChannels(current.filter { it.isCustom })
        _channels.value = current
        if (_selectedChannel.value.id == channel.id) {
            selectChannel(_channels.value.first())
        }
    }

    private fun saveCustomChannels(customList: List<ChannelItem>) {
        try {
            val array = JSONArray()
            for (c in customList) {
                val obj = JSONObject()
                obj.put("id", c.id)
                obj.put("displayName", c.displayName)
                obj.put("serverUrl", c.serverUrl)
                obj.put("roomName", c.roomName)
                obj.put("description", c.description)
                obj.put("badgeColorHex", c.badgeColorHex)
                array.put(obj)
            }
            prefs.edit().putString("custom_channels_json", array.toString()).apply()
        } catch (e: Exception) {
            // Persistence fallback
        }
    }
}
