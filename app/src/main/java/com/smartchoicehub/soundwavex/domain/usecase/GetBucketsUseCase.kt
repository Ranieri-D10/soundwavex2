package com.smartchoicehub.soundwavex.domain.usecase

import com.smartchoicehub.soundwavex.data.model.Bucket
import com.smartchoicehub.soundwavex.data.repository.MusicRepository


class GetBucketsUseCase(private val repo: MusicRepository) {
    suspend operator fun invoke(): List<Bucket> = repo.getBuckets()
}