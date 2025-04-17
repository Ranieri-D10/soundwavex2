package com.smartchoicehub.soundwavex.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.domain.usecase.GetAllSongsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val getAllSongsUseCase: GetAllSongsUseCase) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadSongs()
    }

     fun loadSongs() {
        viewModelScope.launch {
            _songs.value = getAllSongsUseCase()
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
}

class MainViewModelFactory(
    private val useCase: GetAllSongsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(useCase) as T
    }
}
