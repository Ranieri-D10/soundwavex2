package com.smartchoicehub.soundwavex.ui.screen.player

import androidx.lifecycle.ViewModel
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository

class PlayerViewModel(
    private val repository: MusicRepository
) : ViewModel() {

    private var currentSong: Song? = null

    fun play(song: Song) {
        currentSong = song
        repository.playSong(song)
    }

    fun pause() {
        repository.pause()
    }

    fun getCurrentSong(): Song? = currentSong
}
