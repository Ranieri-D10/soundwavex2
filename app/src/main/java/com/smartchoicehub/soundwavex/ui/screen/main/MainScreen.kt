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
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.ui.screen.components.MusicListItem


@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onToggleTheme: () -> Unit,
    onSongClick: (Song) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFolder by remember { mutableStateOf<String?>(null) }

    val folders by mainViewModel.folders.collectAsState()
    val songs by mainViewModel.songs.collectAsState()

    // decide se mostra pastas ou músicas
    val isBrowsingSongs = selectedFolder != null

    LaunchedEffect(Unit) {
        mainViewModel.loadFolders()
    }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        if (!isBrowsingSongs) {
            LazyColumn {
                items(folders) { folder ->
                    Text(
                        text = folder.substringAfterLast('/'),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedFolder = folder
                                mainViewModel.loadSongsByFolder(folder)
                            }
                            .padding(8.dp)
                    )
                    Divider()
                }
            }
        } else {
            val filteredSongs = songs.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.artist.contains(searchQuery, ignoreCase = true)
            }

            TextButton(onClick = {
                selectedFolder = null
                mainViewModel.clearSongs()
            }) {
                Text("⬅ Back to Folders")
            }

            LazyColumn {
                items(filteredSongs) { song ->
                    MusicListItem(song = song, onClick = { onSongClick(song) })
                    Divider()
                }
            }
        }
    }
}