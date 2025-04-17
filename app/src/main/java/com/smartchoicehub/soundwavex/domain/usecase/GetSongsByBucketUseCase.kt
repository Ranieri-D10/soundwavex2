package com.smartchoicehub.soundwavex.domain.usecase

import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository

class GetSongsByBucketUseCase(private val repo: MusicRepository) {
    suspend operator fun invoke(bucketId: Long): List<Song> =
        repo.getSongsByBucket(bucketId)
}