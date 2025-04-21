package com.smartchoicehub.soundwavex.ui.screen.main

import MainViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.ui.screen.components.BucketSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onSongClick: (Song) -> Unit
) {
    val searchQuery by mainViewModel.searchQuery.collectAsState()
    val songs by mainViewModel.songs.collectAsState()
    val buckets by mainViewModel.buckets.collectAsState()
    val filteredSongs = songs.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        mainViewModel.loadSongs()
        mainViewModel.loadBuckets()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Soundwavex") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { mainViewModel.onSearchQueryChanged(it) },
                label = { Text("Search songs") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // Lista de Buckets (pastas)
            if (buckets.isNotEmpty()) {
                BucketSelector(buckets = buckets) { bucketId ->
                    mainViewModel.loadSongsByBucket(bucketId)
                }
            }

            // Lista de músicas
            LazyColumn {
                items(filteredSongs) { song ->
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
}
