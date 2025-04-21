package com.smartchoicehub.soundwavex.ui.screen.main

import MainViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smartchoicehub.soundwavex.domain.usecase.GetAllSongsUseCase
import com.smartchoicehub.soundwavex.domain.usecase.GetBucketsUseCase
import com.smartchoicehub.soundwavex.domain.usecase.GetSongsByBucketUseCase

class MainViewModelFactory(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val getBucketsUseCase: GetBucketsUseCase,
    private val getSongsByBucketUseCase: GetSongsByBucketUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                getAllSongsUseCase,
                getBucketsUseCase,
                getSongsByBucketUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
