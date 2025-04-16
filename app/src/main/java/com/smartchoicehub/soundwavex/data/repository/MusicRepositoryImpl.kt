package com.smartchoicehub.soundwavex.data.repository

import com.smartchoicehub.soundwavex.data.datasource.LocalMusicDataSource
import com.smartchoicehub.soundwavex.data.model.Song

class MusicRepositoryImpl(
    private val localMusicDataSource: LocalMusicDataSource
) : MusicRepository {

    override suspend fun getAllSongs(): List<Song> {
        // Pode ser suspend ou não dependendo de como você usou fetchLocalSongs
        return localMusicDataSource.fetchLocalSongs()
    }

    override fun playSong(song: Song) {
        // Por enquanto você pode deixar vazio ou logar algo
    }

    override fun pause() {
        // Por enquanto também pode deixar vazio
    }
}
