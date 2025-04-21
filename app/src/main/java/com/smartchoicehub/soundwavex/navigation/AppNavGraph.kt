package com.smartchoicehub.soundwavex.navigation

import MainViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartchoicehub.soundwavex.ui.screen.main.MainScreen
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerScreen
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerViewModel
import com.smartchoicehub.soundwavex.ui.screen.settings.SettingsScreen
import com.smartchoicehub.soundwavex.ui.screen.settings.SettingsViewModel

sealed class Screen(val route: String, val icon: ImageVector?) {
    object Main : Screen("main", Icons.Default.Home)
    object Player : Screen("player", Icons.Default.PlayArrow)
    object Settings : Screen("settings", Icons.Default.Settings)
    object CurrentPlayer : Screen("player", Icons.Default.PlayArrow)
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    playerViewModel: PlayerViewModel,
    settingsViewModel: SettingsViewModel,
    onToggleTheme: () -> Unit,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                mainViewModel = mainViewModel,
                onSongClick = { song ->
                    val songs = mainViewModel.songs.value
                    val songsInSameFolder = songs.filter {
                        it.uri.substringBeforeLast("/") == song.uri.substringBeforeLast("/")
                    }

                    playerViewModel.play(song, songsInSameFolder)

                    navController.navigate(Screen.Player.route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Player.route) {
            PlayerScreen(
                viewModel = playerViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = settingsViewModel
            )
        }
    }
}
