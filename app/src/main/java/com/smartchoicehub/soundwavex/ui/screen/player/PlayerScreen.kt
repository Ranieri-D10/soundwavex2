package com.smartchoicehub.soundwavex.ui.screen.player

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartchoicehub.soundwavex.data.model.Song

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel,
    currentSong: Song,
    songList: List<Song>
) {
    // Toca a música ao entrar
    LaunchedEffect(currentSong) {
        viewModel.play(currentSong, songList)
    }

    // Observa o estado de reprodução
    val isPlaying by viewModel.isPlaying.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentSong.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = currentSong.artist)
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        ) {
            Text("Album Art", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { viewModel.playPrevious() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
            }

            IconButton(onClick = {
                if (isPlaying) viewModel.pause() else viewModel.resume()
            }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }

            IconButton(onClick = { viewModel.playNext() }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val shuffleOn by viewModel.shuffleMode.collectAsState()

        IconButton(onClick = { viewModel.toggleShuffle() }) {
            Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "Toggle Shuffle",
                tint = if (shuffleOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}