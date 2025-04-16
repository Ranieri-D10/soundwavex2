package com.smartchoicehub.soundwavex.ui.screen.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.model.Playlist

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel,
    onSongClick: (Song) -> Unit
) {
    val playlists by viewModel.playlists.collectAsState()
    var playlistName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = playlistName,
            onValueChange = { playlistName = it },
            label = { Text("Nova Playlist") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (playlistName.isNotBlank()) {
                    viewModel.createPlaylist(playlistName.trim())
                    playlistName = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Criar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(playlists) { playlist ->
                Text(
                    text = playlist.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                playlist.songs.forEach { song ->
                    ListItem(
                        headlineContent = { Text(song.title) },
                        supportingContent = { Text(song.artist) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSongClick(song) }
                    )
                    Divider()
                }
            }
        }
    }
}
