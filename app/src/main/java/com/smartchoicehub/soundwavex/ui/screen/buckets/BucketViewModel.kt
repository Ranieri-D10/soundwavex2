package com.smartchoicehub.soundwavex.ui.screen.buckets

import android.app.Application
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.smartchoicehub.soundwavex.data.model.Bucket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BucketViewModel(application: Application) : AndroidViewModel(application) {
    private val _buckets = MutableStateFlow<List<Bucket>>(emptyList())
    val buckets: StateFlow<List<Bucket>> = _buckets

    fun loadBuckets() {
        viewModelScope.launch {
            val bucketList = mutableListOf<Bucket>()
            val projection = arrayOf(
                MediaStore.Audio.Media.BUCKET_ID,
                MediaStore.Audio.Media.BUCKET_DISPLAY_NAME
            )
            val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

            val cursor = getApplication<Application>().contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null
            )

            cursor?.use {
                val bucketIdColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.BUCKET_ID)
                val bucketNameColumn = it.getColumnIndexOrThrow(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME)

                val seen = mutableSetOf<Long>()

                while (it.moveToNext()) {
                    val id = it.getLong(bucketIdColumn)
                    val name = it.getString(bucketNameColumn)

                    if (!seen.contains(id)) {
                        bucketList.add(Bucket(id, name))
                        seen.add(id)
                    }
                }
            }

            _buckets.value = bucketList
        }
    }
}