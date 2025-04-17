package com.smartchoicehub.soundwavex

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.smartchoicehub.soundwavex.data.repository.MusicRepositoryImpl
import com.smartchoicehub.soundwavex.domain.usecase.GetAllSongsUseCase
import com.smartchoicehub.soundwavex.navigation.AppNavGraph
import com.smartchoicehub.soundwavex.ui.screen.components.NavigationDrawer
import com.smartchoicehub.soundwavex.ui.screen.main.MainViewModel
import com.smartchoicehub.soundwavex.ui.screen.main.MainViewModelFactory
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerViewModel
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerViewModelFactory
import com.smartchoicehub.soundwavex.ui.screen.playlist.PlaylistViewModel
import com.smartchoicehub.soundwavex.ui.theme.SoundwavexTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        requestAudioPermission()

        val musicRepo = MusicRepositoryImpl(this)
        val mainViewModelFactory = MainViewModelFactory(musicRepo)
        val playerViewModelFactory = PlayerViewModelFactory(musicRepo)

        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            var isDrawerOpen by remember { mutableStateOf(false) }

            SoundwavexTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
                val playerViewModel: PlayerViewModel = viewModel(factory = playerViewModelFactory)
                val playlistViewModel: PlaylistViewModel = viewModel()

                NavigationDrawer(
                    isDark = isDarkMode,
                    showDrawer = isDrawerOpen,
                    onDismiss = { isDrawerOpen = false },
                    onToggleTheme = { isDarkMode = !isDarkMode },
                    onNavigateToPlaylist = {
                        navController.navigate("playlist")
                        isDrawerOpen = false
                    },
                    onNavigateToHome = {
                        navController.navigate("main")
                        isDrawerOpen = false
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                title = { Text("Soundwavex") },
                                actions = {
                                    IconButton(onClick = { isDrawerOpen = true }) {
                                        Icon(Icons.Default.MoreVert, contentDescription = "Abrir Drawer")
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavGraph(
                                navController = navController,
                                mainViewModel = mainViewModel,
                                playerViewModel = playerViewModel,
                                playlistViewModel = playlistViewModel,
                                onToggleTheme = { isDarkMode = !isDarkMode }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun requestAudioPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission), 0)
        }
    }
}
