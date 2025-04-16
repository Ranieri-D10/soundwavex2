package com.smartchoicehub.soundwavex.ui.screen.playlist

import androidx.lifecycle.ViewModel
import com.smartchoicehub.soundwavex.data.model.Playlist
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.domain.usecase.ManagePlaylistUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PlaylistViewModel : ViewModel() {

    private val playlistUseCase = ManagePlaylistUseCase()

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists

    fun createPlaylist(name: String) {
        val new = playlistUseCase.createPlaylist(name)
        _playlists.value = _playlists.value + new
    }

    fun addToPlaylist(song: Song, playlistName: String) {
        _playlists.value = _playlists.value.map {
            if (it.name == playlistName) {
                playlistUseCase.addSongToPlaylist(song, it)
                it
            } else it
        }
    }

    fun removeFromPlaylist(song: Song, playlistName: String) {
        _playlists.value = _playlists.value.map {
            if (it.name == playlistName) {
                playlistUseCase.removeSongFromPlaylist(song, it)
                it
            } else it
        }
    }
}
