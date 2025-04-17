package com.smartchoicehub.soundwavex.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.smartchoicehub.soundwavex.data.model.Song

@Composable
fun SongListItem(song: Song, onClick: () -> Unit) {
    androidx.compose.material3.ListItem(
        headlineContent = { Text(song.title) },
        supportingContent = { Text(song.artist) },
        modifier = Modifier.clickable { onClick() }
    )
}
