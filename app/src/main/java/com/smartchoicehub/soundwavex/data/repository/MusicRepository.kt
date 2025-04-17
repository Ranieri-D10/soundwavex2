package com.smartchoicehub.soundwavex.data.repository

import com.smartchoicehub.soundwavex.data.model.Song

interface MusicRepository {
    suspend fun getSongs(): List<Song>
    fun playSong(song: Song)
    fun pause()
}