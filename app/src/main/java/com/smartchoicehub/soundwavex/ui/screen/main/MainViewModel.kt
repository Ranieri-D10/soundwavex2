import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartchoicehub.soundwavex.data.model.Song
import com.smartchoicehub.soundwavex.data.model.Bucket
import com.smartchoicehub.soundwavex.domain.usecase.GetAllSongsUseCase
import com.smartchoicehub.soundwavex.domain.usecase.GetBucketsUseCase
import com.smartchoicehub.soundwavex.domain.usecase.GetSongsByBucketUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val getBucketsUseCase: GetBucketsUseCase,
    private val getSongsByBucketUseCase: GetSongsByBucketUseCase
) : ViewModel() {

    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    private val _buckets = MutableStateFlow<List<Bucket>>(emptyList())
    val buckets: StateFlow<List<Bucket>> = _buckets

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

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

    fun loadBuckets() {
        viewModelScope.launch {
            _buckets.value = getBucketsUseCase()
        }
    }

    fun loadSongsByBucket(bucketId: Long) {
        viewModelScope.launch {
            _songs.value = getSongsByBucketUseCase(bucketId)
        }
    }
}
