package com.smartchoicehub.soundwavex.ui.screen.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            ListItem(
                headlineContent = { Text("Dark Theme") },
                supportingContent = { Text("Toggle between light and dark theme") },
                trailingContent = {
                    Switch(
                        checked = viewModel.isDarkTheme,
                        onCheckedChange = { viewModel.toggleTheme() }
                    )
                }
            )
        }
    }
}
