package com.smartchoicehub.soundwavex.ui.screen.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val repository: MusicRepository
) : ViewModel() {

    private val _currentSong = MutableStateFlow<Song?>(null)
    val currentSong: StateFlow<Song?> = _currentSong

    private val _currentPlaylist = MutableStateFlow<List<Song>>(emptyList())
    val currentPlaylist: StateFlow<List<Song>> = _currentPlaylist

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _shuffleMode = MutableStateFlow(false)
    val shuffleMode: StateFlow<Boolean> = _shuffleMode

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    private var currentIndex = 0

    init {
        observePlaybackPosition()
    }

    private fun observePlaybackPosition() {
        viewModelScope.launch {
            while (true) {
                _isPlaying.value.let { playing ->
                    if (playing) {
                        _currentPosition.value = repository.getCurrentPosition()
                    }
                }
                delay(500L)
            }
        }
    }

    fun play(song: Song, songsInFolder: List<Song>) {
        _currentPlaylist.value = songsInFolder
        currentIndex = songsInFolder.indexOf(song)
        _currentSong.value = song
        _currentPosition.value = 0L

        repository.playSong(song) {
            playNext()
        }

        _isPlaying.value = true
    }

    fun pause() {
        repository.pause()
        _isPlaying.value = false
    }

    fun resume() {
        _currentSong.value?.let {
            repository.playSong(it) {
                playNext()
            }
            _isPlaying.value = true
        }
    }

    fun playNext() {
        val list = _currentPlaylist.value
        if (list.isEmpty()) return

        currentIndex = if (_shuffleMode.value) {
            (list.indices - currentIndex).random()
        } else {
            (currentIndex + 1) % list.size
        }

        play(list[currentIndex], list)
    }

    fun playPrevious() {
        val list = _currentPlaylist.value
        if (list.isEmpty()) return

        currentIndex = if (currentIndex - 1 < 0) list.lastIndex else currentIndex - 1
        play(list[currentIndex], list)
    }

    fun toggleShuffle() {
        _shuffleMode.value = !_shuffleMode.value
    }

    fun seekTo(position: Long) {
        repository.seekTo(position)
        _currentPosition.value = position
    }
}
