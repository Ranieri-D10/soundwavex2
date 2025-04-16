package com.smartchoicehub.soundwavex.domain.usecase

import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository

class GetAllSongsUseCase(private val repository: MusicRepository) {
    suspend operator fun invoke(): List<Song> {
        return repository.getAllSongs()
    }
}
