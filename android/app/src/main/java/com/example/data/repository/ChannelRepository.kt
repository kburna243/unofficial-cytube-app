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
            id = "420Grindhouse",
            displayName = "420 Grindhouse",
            serverUrl = "https://cytu.be",
            roomName = "420Grindhouse",
            description = "B-Movies, Cult Cinema, Exploitation & Kung-Fu",
            badgeColorHex = "#00E676",
            hasKrytenQueue = true
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
        val selectedRoom = prefs.getString("selected_room", "420Grindhouse") ?: "420Grindhouse"
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

    /**
     * Fetches public live channels from https://cytu.be/ and dynamically updates the channel list
     * while preserving custom channels, user counts, and current room selection.
     */
    suspend fun refreshPublicChannels(): Result<List<ChannelItem>> = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        try {
            val client = okhttp3.OkHttpClient.Builder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .build()

            val req = okhttp3.Request.Builder()
                .url("https://cytu.be/")
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 14) CyTubeTV/1.0")
                .build()

            val response = client.newCall(req).execute()
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("HTTP ${response.code}"))
            }

            val html = response.body?.string().orEmpty()
            val parsedList = parseChannelsFromHtml(html)

            if (parsedList.isNotEmpty()) {
                val customList = _channels.value.filter { it.isCustom }
                val merged = mutableListOf<ChannelItem>()
                merged.addAll(parsedList)

                // Add custom channels if not already in parsed list
                for (custom in customList) {
                    if (merged.none { it.roomName.equals(custom.roomName, ignoreCase = true) }) {
                        merged.add(custom)
                    }
                }

                _channels.value = merged

                // Update selected channel reference with updated info
                val current = _selectedChannel.value
                val matched = merged.find { it.roomName.equals(current.roomName, ignoreCase = true) }
                if (matched != null) {
                    _selectedChannel.value = matched
                }
                return@withContext Result.success(merged)
            }
            Result.failure(Exception("No channels found in HTML"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseChannelsFromHtml(html: String): List<ChannelItem> {
        val tableMatch = java.util.regex.Pattern.compile("<table[^>]*>(.*?)</table>", java.util.regex.Pattern.DOTALL).matcher(html)
        if (!tableMatch.find()) return emptyList()

        val tableContent = tableMatch.group(1) ?: return emptyList()
        val rowMatcher = java.util.regex.Pattern.compile("<tr[^>]*>(.*?)</tr>", java.util.regex.Pattern.DOTALL).matcher(tableContent)

        val result = mutableListOf<ChannelItem>()
        val palette = listOf("#00E676", "#FF5722", "#AB47BC", "#FFCA28", "#66BB6A", "#42A5F5", "#7E57C2", "#FFA726", "#EC407A", "#26C6DA")
        var colorIdx = 0

        while (rowMatcher.find()) {
            val row = rowMatcher.group(1) ?: continue
            val roomMatch = java.util.regex.Pattern.compile("href=[\"']/r/([^\"']+)[\"']").matcher(row)
            if (!roomMatch.find()) continue

            val room = roomMatch.group(1)?.trim().orEmpty()
            if (room.isBlank()) continue

            val colMatcher = java.util.regex.Pattern.compile("<td[^>]*>(.*?)</td>", java.util.regex.Pattern.DOTALL).matcher(row)
            val cols = mutableListOf<String>()
            while (colMatcher.find()) {
                cols.add(colMatcher.group(1).orEmpty())
            }
            if (cols.isEmpty()) continue

            var titleRaw = cols[0].replace(Regex("<[^>]+>"), " ").trim()
            titleRaw = unescapeHtml(titleRaw)
            val cleanTitle = titleRaw.replace(Regex("\\s*\\(" + java.util.regex.Pattern.quote(room) + "\\)$"), "").trim()

            var userCount = 0
            if (cols.size > 1) {
                val uStr = cols[1].replace(Regex("<[^>]+>"), "").trim()
                userCount = uStr.toIntOrNull() ?: 0
            }

            var nowPlaying = ""
            if (cols.size > 2) {
                nowPlaying = unescapeHtml(cols[2].replace(Regex("<[^>]+>"), "").trim())
            }

            val isKryten = room.equals("420Grindhouse", ignoreCase = true)
            val color = palette[colorIdx % palette.size]
            colorIdx++

            val desc = if (nowPlaying.isNotBlank()) "Now: $nowPlaying" else "cytu.be/r/$room"

            result.add(
                ChannelItem(
                    id = room,
                    displayName = if (cleanTitle.isNotBlank()) cleanTitle else room,
                    serverUrl = "https://cytu.be",
                    roomName = room,
                    description = desc,
                    badgeColorHex = color,
                    isCustom = false,
                    hasKrytenQueue = isKryten,
                    userCount = userCount,
                    nowPlaying = nowPlaying
                )
            )
        }
        return result
    }

    private fun unescapeHtml(text: String): String {
        return text
            .replace("&amp;", "&")
            .replace("&quot;", "\"")
            .replace("&#39;", "'")
            .replace("&apos;", "'")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#32;", " ")
            .replace("&nbsp;", " ")
    }
}

