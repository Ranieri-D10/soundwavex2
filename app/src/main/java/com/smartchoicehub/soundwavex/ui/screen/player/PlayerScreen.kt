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

@Composable fun PlayerScreen( viewModel: PlayerViewModel, currentSong: Song ) { LaunchedEffect(currentSong) { viewModel.play(currentSong) }


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
            Text("Capa do Álbum", modifier = Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Anterior")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Tocar")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Próxima")
            }
        }
    }
}