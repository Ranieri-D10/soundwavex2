package com.smartchoicehub.soundwavex.ui.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.data.model.Song

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onToggleTheme: () -> Unit,
    onSongClick: (Song) -> Unit
) {
    val songs by mainViewModel.songs.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Soundwavex") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(Icons.Default.Settings, contentDescription = "Toggle Theme")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            items(songs) { song ->
                Text(
                    text = "${song.title} - ${song.artist}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSongClick(song) }
                        .padding(8.dp)
                )
            }
        }
    }
}
