package com.smartchoicehub.soundwavex.domain.usecase

import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository

class PlaySongUseCase(private val repository: MusicRepository) {
    operator fun invoke(song: Song, onCompletion: () -> Unit = {}) {
        repository.playSong(song, onCompletion)
    }
}