package com.smartchoicehub.soundwavex.ui.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets

@Composable
fun NavigationDrawer(
    isDark: Boolean,
    showDrawer: Boolean,
    onDismiss: () -> Unit,
    onToggleTheme: () -> Unit,
    onNavigateToPlaylist: () -> Unit,
    onNavigateToHome: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = showDrawer,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable { onDismiss() }
            )

            Surface(
                tonalElevation = 4.dp,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .align(Alignment.TopEnd)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(WindowInsets.systemBars.asPaddingValues()) // <-- aqui o segredo
                ) {
                    Text(
                        text = "Configurações",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    NavigationDrawerItem(
                        label = { Text("Início") },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        selected = false,
                        onClick = {
                            onNavigateToHome()
                            onDismiss()
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text("Playlists") },
                        icon = { Icon(Icons.Default.PlaylistPlay, contentDescription = null) },
                        selected = false,
                        onClick = {
                            onNavigateToPlaylist()
                            onDismiss()
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text(if (isDark) "Modo Claro" else "Modo Escuro") },
                        icon = { Icon(Icons.Default.Brightness6, contentDescription = null) },
                        selected = false,
                        onClick = {
                            onToggleTheme()
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}
