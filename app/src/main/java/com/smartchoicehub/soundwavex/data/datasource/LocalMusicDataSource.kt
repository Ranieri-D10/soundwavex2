package com.smartchoicehub.soundwavex.data.datasource

import android.content.Context
import android.provider.MediaStore
import com.smartchoicehub.soundwavex.data.model.Song

class LocalMusicDataSource(private val context: Context) {
    fun fetchLocalSongs(): List<Song> {
        val songs = mutableListOf<Song>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder
        )?.use { cursor ->
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val song = Song(
                    id = cursor.getLong(idIndex),
                    title = cursor.getString(titleIndex),
                    artist = cursor.getString(artistIndex),
                    uri = cursor.getString(dataIndex),
                    duration = cursor.getLong(durationIndex)
                )
                songs.add(song)
            }
        }

        return songs
    }
}