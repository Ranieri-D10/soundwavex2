package com.smartchoicehub.soundwavex.ui.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.ui.screen.components.MusicListItem


@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onToggleTheme: () -> Unit,
    onSongClick: (com.smartchoicehub.soundwavex.data.model.Song) -> Unit,
    modifier: Modifier = Modifier
) {
    // 🔁 Chama loadSongs apenas uma vez ao abrir a tela
    LaunchedEffect(Unit) {
        mainViewModel.loadSongs()
    }

    val songs by mainViewModel.songs.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredSongs = songs.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.artist.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar música") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredSongs) { song ->
                MusicListItem(
                    song = song,
                    onClick = { onSongClick(song) }
                )
                Divider()
            }
        }
    }
}