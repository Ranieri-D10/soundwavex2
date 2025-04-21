package com.smartchoicehub.soundwavex.ui.screen.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.smartchoicehub.soundwavex.navigation.Screen

@Composable
fun BottomNavBar(
    navController: NavController
) {
    val items = listOf(
        Screen.Main,
        Screen.CurrentPlayer,
        Screen.Settings
    )

    NavigationBar {
        val currentDestination = navController.currentBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    screen.icon?.let { Icon(it, contentDescription = null) }
                },
                selected = currentDestination == screen.route,
                onClick = {
                    if (screen == Screen.Main) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
