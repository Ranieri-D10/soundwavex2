package com.smartchoicehub.soundwavex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartchoicehub.soundwavex.ui.screen.main.MainScreen
import com.smartchoicehub.soundwavex.ui.screen.main.MainViewModel
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerScreen
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerViewModel
import com.smartchoicehub.soundwavex.ui.screen.playlist.PlaylistScreen
import com.smartchoicehub.soundwavex.ui.screen.playlist.PlaylistViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Player : Screen("player/{songId}")
    object Playlist : Screen("playlist")
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    playerViewModel: PlayerViewModel,
    playlistViewModel: PlaylistViewModel,
    onToggleTheme: () -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                mainViewModel = mainViewModel,
                onToggleTheme = onToggleTheme,
                onSongClick = { song ->
                    navController.navigate(Screen.Player.route.replace("{songId}", song.id.toString()))
                }
            )
        }

        composable(Screen.Player.route) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId")?.toLongOrNull()
            val songs by mainViewModel.songs.collectAsState()
            val song = songs.find { it.id == songId }
            if (song != null) {
                val songsInSameFolder = songs.filter {
                    it.uri.substringBeforeLast("/") == song.uri.substringBeforeLast("/")
                }
                PlayerScreen(
                    viewModel = playerViewModel,
                    currentSong = song,
                    songList = songsInSameFolder
                )
            }
        }

        composable(Screen.Playlist.route) {
            PlaylistScreen(
                viewModel = playlistViewModel,
                onSongClick = { song ->
                    navController.navigate(Screen.Player.route.replace("{songId}", song.id.toString()))
                }
            )
        }
    }
}
