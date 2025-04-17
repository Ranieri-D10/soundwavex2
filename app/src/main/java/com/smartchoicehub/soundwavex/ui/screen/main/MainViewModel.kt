package com.smartchoicehub.soundwavex.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.repository.MusicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MusicRepository) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _folders = MutableStateFlow<List<String>>(emptyList())
    val folders: StateFlow<List<String>> = _folders

    init {
        loadSongs()
    }

    fun loadSongs() {
        viewModelScope.launch {
            _songs.value = repository.getSongs()
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredSongs(): List<Song> {
        return _songs.value.filter {
            it.title.contains(_searchQuery.value, ignoreCase = true)
        }
    }

    fun loadFolders() {
        viewModelScope.launch {
            _folders.value = repository.getFolders()
        }
    }

    fun loadSongsByFolder(folderPath: String) {
        viewModelScope.launch {
            _songs.value = repository.getSongsByFolder(folderPath)
        }
    }

    fun clearSongs() {
        _songs.value = emptyList()
    }
}
