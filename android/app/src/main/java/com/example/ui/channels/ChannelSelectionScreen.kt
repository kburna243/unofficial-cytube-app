package com.example.ui.channels

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import com.example.data.model.ChannelItem
import com.example.ui.theme.AccentCoral
import com.example.ui.theme.AccentIceBlue
import com.example.ui.theme.AccentLavender
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentVibrantOrange
import com.example.ui.theme.MidnightCanvas
import com.example.ui.theme.PureWhite
import com.example.ui.theme.StatusLiveGreen
import com.example.ui.theme.SubtleBorder
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtitleWhite
import kotlinx.coroutines.delay

@Composable
fun ChannelSelectionScreen(
    channels: List<ChannelItem>,
    currentChannel: ChannelItem?,
    onSelectChannel: (ChannelItem) -> Unit,
    onAddChannel: (name: String, room: String) -> Unit,
    onDeleteChannel: (ChannelItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    val resumeFocusRequester = remember { FocusRequester() }
    val focusRequesters = remember(channels.size) {
        List(channels.size + 4) { FocusRequester() }
    }

    LaunchedEffect(Unit) {
        var focused = false
        var attempts = 0
        while (!focused && attempts < 25) {
            delay(100)
            attempts++
            try {
                if (currentChannel != null) {
                    resumeFocusRequester.requestFocus()
                    focused = true
                } else if (focusRequesters.isNotEmpty()) {
                    focusRequesters[0].requestFocus()
                    focused = true
                }
            } catch (_: Exception) {
                // Retry until layout attachment
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MidnightCanvas)
            .padding(horizontal = 44.dp, vertical = 28.dp)
            .testTag("channel_selection_screen")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Top Header with Title & Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF9D65FF), Color(0xFFA675FF))
                                ),
                                RoundedCornerShape(14.dp)
                            )
                            .border(1.5.dp, Color(0xFFD4BBFF), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LiveTv,
                            contentDescription = null,
                            tint = PureWhite,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "CYTUBE TV",
                            style = TextStyle(
                                color = PureWhite,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 26.sp,
                                letterSpacing = 2.sp
                            )
                        )
                        Text(
                            text = "Channel Selection Hub • Zap with D-Pad ▲/▼ during playback",
                            style = TextStyle(
                                color = TextMuted,
                                fontSize = 13.sp
                            )
                        )
                    }
                }

                // Action Buttons (Resume Playback & Add Channel)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Quick Resume Button
                    if (currentChannel != null) {
                        val resumeInteraction = remember { MutableInteractionSource() }
                        val isResumeFocused by resumeInteraction.collectIsFocusedAsState()

                        Surface(
                            color = if (isResumeFocused) AccentIceBlue else Color(0xFF1E1830),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(
                                width = if (isResumeFocused) 2.5.dp else 1.5.dp,
                                color = if (isResumeFocused) PureWhite else AccentIceBlue.copy(alpha = 0.6f)
                            ),
                            modifier = Modifier
                                .height(44.dp)
                                .scale(if (isResumeFocused) 1.05f else 1.0f)
                                .focusRequester(resumeFocusRequester)
                                .focusable(interactionSource = resumeInteraction)
                                .clickable(
                                    interactionSource = resumeInteraction,
                                    indication = null,
                                    onClick = { onSelectChannel(currentChannel) }
                                )
                                .onKeyEvent { keyEvent ->
                                    if (keyEvent.type == KeyEventType.KeyDown) {
                                        when (keyEvent.key) {
                                            Key.DirectionCenter, Key.Enter, Key.NumPadEnter -> {
                                                onSelectChannel(currentChannel)
                                                true
                                            }
                                            else -> false
                                        }
                                    } else false
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = if (isResumeFocused) Color.Black else StatusLiveGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Watch ${currentChannel.displayName}",
                                    style = TextStyle(
                                        color = if (isResumeFocused) Color.Black else PureWhite,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 13.sp
                                    )
                                )
                            }
                        }
                    }

                    // Add Channel Button
                    val addInteraction = remember { MutableInteractionSource() }
                    val isAddFocused by addInteraction.collectIsFocusedAsState()

                    Surface(
                        color = if (isAddFocused) AccentIceBlue else SurfaceCard,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            width = if (isAddFocused) 2.5.dp else 1.5.dp,
                            color = if (isAddFocused) PureWhite else Color.White.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier
                            .height(44.dp)
                            .scale(if (isAddFocused) 1.05f else 1.0f)
                            .focusable(interactionSource = addInteraction)
                            .clickable(
                                interactionSource = addInteraction,
                                indication = null,
                                onClick = { showAddDialog = true }
                            )
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.type == KeyEventType.KeyDown) {
                                    when (keyEvent.key) {
                                        Key.DirectionCenter, Key.Enter, Key.NumPadEnter -> {
                                            showAddDialog = true
                                            true
                                        }
                                        else -> false
                                    }
                                } else false
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = if (isAddFocused) Color.Black else AccentLavender,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "+ Add Channel",
                                style = TextStyle(
                                    color = if (isAddFocused) Color.Black else PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Channel Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 280.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(channels, key = { _, item -> item.id }) { index, channel ->
                    val requester = focusRequesters.getOrNull(index) ?: remember { FocusRequester() }
                    ChannelCard(
                        channel = channel,
                        isSelected = currentChannel?.roomName.equals(channel.roomName, ignoreCase = true),
                        focusRequester = requester,
                        onSelect = { onSelectChannel(channel) },
                        onDelete = { onDeleteChannel(channel) }
                    )
                }
            }

            // 3. Bottom Remote Navigation Controls Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, SubtleBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        RemoteKeyBadge("▲▼◄►", "Navigate")
                        RemoteKeyBadge("OK", "Select Channel")
                        RemoteKeyBadge("≡ / MENU", "Add Channel")
                        RemoteKeyBadge("⮌ BACK", "Exit App")
                    }
                }
            }
        }

        // Add Channel Dialog Modal
        if (showAddDialog) {
            AddChannelDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, room ->
                    onAddChannel(name, room)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun ChannelCard(
    channel: ChannelItem,
    isSelected: Boolean,
    focusRequester: FocusRequester,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val badgeColor = remember(channel.badgeColorHex) {
        try {
            Color(android.graphics.Color.parseColor(channel.badgeColorHex))
        } catch (e: Exception) {
            AccentIceBlue
        }
    }

    Surface(
        color = when {
            isFocused -> Color(0xFF38235C)
            isSelected -> Color(0xFF24163E)
            else -> SurfaceCard.copy(alpha = 0.90f)
        },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = if (isFocused) 2.5.dp else if (isSelected) 1.5.dp else 1.dp,
            color = when {
                isFocused -> PureWhite
                isSelected -> AccentLavender.copy(alpha = 0.8f)
                else -> Color.White.copy(alpha = 0.08f)
            }
        ),
        shadowElevation = if (isFocused) 32.dp else 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(165.dp)
            .scale(if (isFocused) 1.04f else 1.0f)
            .focusRequester(focusRequester)
            .focusable(interactionSource = interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onSelect
            )
            .onKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown) {
                    when (keyEvent.key) {
                        Key.DirectionCenter, Key.Enter, Key.NumPadEnter -> {
                            onSelect()
                            true
                        }
                        else -> false
                    }
                } else false
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Badge Icon + Name
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(
                                if (isFocused) AccentIceBlue.copy(alpha = 0.25f) else badgeColor.copy(alpha = 0.20f),
                                RoundedCornerShape(10.dp)
                            )
                            .border(
                                1.dp,
                                if (isFocused) AccentIceBlue else badgeColor.copy(alpha = 0.4f),
                                RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tv,
                            contentDescription = null,
                            tint = if (isFocused) PureWhite else badgeColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = channel.displayName,
                            style = TextStyle(
                                color = PureWhite,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "cytu.be/r/${channel.roomName}",
                            style = TextStyle(
                                color = if (isFocused) AccentLavender else TextMuted,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }
                }

                if (channel.isCustom) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = AccentCoral.copy(alpha = 0.8f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else if (channel.userCount > 0) {
                    Box(
                        modifier = Modifier
                            .background(StatusLiveGreen.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                            .border(1.dp, StatusLiveGreen.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(StatusLiveGreen, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "${channel.userCount} LIVE",
                                style = TextStyle(
                                    color = StatusLiveGreen,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }
                    }
                } else if (isSelected) {
                    Box(
                        modifier = Modifier
                            .background(AccentIceBlue.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                            .border(1.dp, AccentIceBlue.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "ACTIVE",
                            style = TextStyle(
                                color = AccentLavender,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }
            }

            // Description
            Text(
                text = channel.description.ifBlank { "Live CyTube Community Room" },
                style = TextStyle(
                    color = if (isFocused) PureWhite else TextSubtitleWhite.copy(alpha = 0.85f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Play hint
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = if (isFocused) StatusLiveGreen else TextMuted,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isFocused) "▶ PRESS OK TO WATCH" else "Watch Stream",
                    style = TextStyle(
                        color = if (isFocused) StatusLiveGreen else TextMuted,
                        fontSize = 11.sp,
                        fontWeight = if (isFocused) FontWeight.ExtraBold else FontWeight.Normal,
                        letterSpacing = if (isFocused) 0.5.sp else 0.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun RemoteKeyBadge(key: String, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White.copy(alpha = 0.15f))
                .border(1.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = key,
                style = TextStyle(
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = PureWhite
                )
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 10.sp,
                color = PureWhite.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
fun AddChannelDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, room: String) -> Unit
) {
    var roomInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = SurfaceDark,
        title = {
            Text(
                text = "Add Custom CyTube Channel",
                style = TextStyle(
                    color = PureWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Enter CyTube Room Name (e.g. 'Channel-Z' or '420Grindhouse'):",
                    style = TextStyle(color = TextSubtitleWhite, fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = roomInput,
                    onValueChange = { roomInput = it },
                    placeholder = { Text("Room Name", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Display Name (optional):",
                    style = TextStyle(color = TextSubtitleWhite, fontSize = 12.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    placeholder = { Text("Custom Channel Name", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (roomInput.isNotBlank()) {
                        onConfirm(nameInput.ifBlank { roomInput.trim() }, roomInput.trim())
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentPurple)
            ) {
                Text("Add & Watch", color = PureWhite)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextMuted)
            }
        }
    )
}
