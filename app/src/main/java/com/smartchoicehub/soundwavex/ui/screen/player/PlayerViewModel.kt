package com.smartchoicehub.soundwavex.ui.screen.player

import androidx.lifecycle.ViewModel
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel(
    private val repository: MusicRepository
) : ViewModel() {

    private var currentSong: Song? = null
    private var songList: List<Song> = emptyList()
    private var currentIndex = 0

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _shuffleMode = MutableStateFlow(false)
    val shuffleMode: StateFlow<Boolean> = _shuffleMode

    fun play(song: Song, songsInFolder: List<Song>) {
        songList = songsInFolder
        currentIndex = songList.indexOf(song)
        currentSong = song
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
        currentSong?.let {
            repository.playSong(it) {
                playNext()
            }
            _isPlaying.value = true
        }
    }

    fun playNext() {
        if (songList.isEmpty()) return
        currentIndex = if (_shuffleMode.value) {
            (songList.indices - currentIndex).random()
        } else {
            (currentIndex + 1) % songList.size
        }
        play(songList[currentIndex], songList)
    }

    fun playPrevious() {
        if (songList.isEmpty()) return
        currentIndex = if (currentIndex - 1 < 0) songList.lastIndex else currentIndex - 1
        play(songList[currentIndex], songList)
    }

    fun toggleShuffle() {
        _shuffleMode.value = !_shuffleMode.value
    }

    fun getCurrentSong(): Song? = currentSong
}