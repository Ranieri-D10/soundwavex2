package com.smartchoicehub.soundwavex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.smartchoicehub.soundwavex.data.repository.MusicRepositoryImpl
import com.smartchoicehub.soundwavex.navigation.AppNavGraph
import com.smartchoicehub.soundwavex.ui.screen.components.BottomNavBar
import com.smartchoicehub.soundwavex.ui.screen.main.MainViewModel
import com.smartchoicehub.soundwavex.ui.screen.main.MainViewModelFactory
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerViewModel
import com.smartchoicehub.soundwavex.ui.screen.player.PlayerViewModelFactory
import com.smartchoicehub.soundwavex.ui.screen.settings.SettingsViewModel
import com.smartchoicehub.soundwavex.ui.theme.SoundwavexTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val musicRepository = MusicRepositoryImpl(applicationContext)

            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(musicRepository)
            )
            val playerViewModel: PlayerViewModel = viewModel(
                factory = PlayerViewModelFactory(musicRepository)
            )
            val settingsViewModel: SettingsViewModel = viewModel()


            // 🎯 Observe diretamente o tema do ViewModel
            val isDarkTheme = settingsViewModel.isDarkTheme

            SoundwavexTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController = navController)
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        playerViewModel = playerViewModel,
                        settingsViewModel = settingsViewModel,
                        onToggleTheme = {
                            settingsViewModel.toggleTheme()
                        },
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}
