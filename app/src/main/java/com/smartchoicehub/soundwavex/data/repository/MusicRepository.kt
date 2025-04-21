package com.smartchoicehub.soundwavex.data.repository

import com.smartchoicehub.soundwavex.data.model.Bucket
import com.smartchoicehub.soundwavex.data.model.Song

interface MusicRepository {
    suspend fun getSongs(): List<Song>
    suspend fun getSongsByFolder(folderPath: String): List<Song>
    suspend fun getFolders(): List<String>
    suspend fun getBuckets(): List<Bucket>
    suspend fun getSongsByBucket(bucketId: Long): List<Song>
    fun playSong(song: Song, onCompletion: () -> Unit = {})
    fun pause()
    fun releasePlayer()
    fun resume()
    fun getCurrentPosition(): Long
    fun seekTo(position: Long)
}