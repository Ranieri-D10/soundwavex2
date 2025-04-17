package com.smartchoicehub.soundwavex.ui.screen.buckets

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.ui.screen.components.SongListItem

@Composable
fun SongsByBucketScreen(
    bucketId: Long,
    viewModel: SongsByBucketViewModel,
    onSongClick: (Song) -> Unit
) {
    // Chama o carregamento das músicas ao montar o Composable
    LaunchedEffect(bucketId) { viewModel.load(bucketId) }

    // Observa a lista de músicas no viewModel
    val songs by viewModel.songs.collectAsState()

    LazyColumn {
        items(songs) { song ->
            SongListItem(song = song) { onSongClick(song) }
            Divider()
        }
    }
}
