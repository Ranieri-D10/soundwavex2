package com.smartchoicehub.soundwavex.ui.screen.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    screen.icon?.let { Icon(it, contentDescription = null) }
                },
                selected = false,
                onClick = {
                    if (screen is Screen.Player) {
                        navController.navigate("player") {
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().route!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
