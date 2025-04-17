package com.smartchoicehub.soundwavex.ui.screen.buckets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.domain.usecase.GetSongsByBucketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongsByBucketViewModel(
    private val getSongsByBucket: GetSongsByBucketUseCase
) : ViewModel() {
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    fun load(bucketId: Long) {
        viewModelScope.launch {
            _songs.value = getSongsByBucket(bucketId)
        }
    }
}