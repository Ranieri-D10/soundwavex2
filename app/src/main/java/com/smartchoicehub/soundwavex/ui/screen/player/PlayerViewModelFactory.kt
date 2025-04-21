package com.smartchoicehub.soundwavex.ui.screen.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smartchoicehub.soundwavex.data.repository.MusicRepository

class PlayerViewModelFactory(
    private val repository: MusicRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
