package com.smartchoicehub.soundwavex.data.datasource

import android.content.Context
import android.provider.MediaStore
import com.smartchoicehub.soundwavex.data.model.Bucket
import com.smartchoicehub.soundwavex.data.model.Song

class LocalMusicDataSource(private val context: Context) {

    // 1) lista de buckets (pastas)
    fun getBuckets(): List<Bucket> {
        val buckets = mutableListOf<Bucket>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media.BUCKET_ID,
            MediaStore.Audio.Media.BUCKET_DISPLAY_NAME
        )
        // distinct via groupBy in code
        val cursor = context.contentResolver.query(
            uri, projection, null, null,
            "${MediaStore.Audio.Media.BUCKET_DISPLAY_NAME} ASC"
        )
        val seen = mutableSetOf<Long>()
        cursor?.use {
            val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.BUCKET_ID)
            val nameCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME)
            while (it.moveToNext()) {
                val bucketId = it.getLong(idCol)
                if (bucketId !in seen) {
                    seen += bucketId
                    val name = it.getString(nameCol) ?: "Unknown"
                    buckets += Bucket(bucketId, name)
                }
            }
        }
        return buckets
    }

    // 2) músicas de um bucket específico
    fun getSongsByBucket(bucketId: Long): List<Song> {
        val songs = mutableListOf<Song>()
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        )
        val selection = ("${MediaStore.Audio.Media.IS_MUSIC} != 0"
                + " AND ${MediaStore.Audio.Media.BUCKET_ID} = ?")
        val selectionArgs = arrayOf(bucketId.toString())
        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

        val cursor = context.contentResolver.query(
            uri, projection, selection, selectionArgs, sortOrder
        )
        cursor?.use {
            val idCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val dataCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val durCol = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            while (it.moveToNext()) {
                val id = it.getLong(idCol)
                val title = it.getString(titleCol) ?: "Unknown"
                val artist = it.getString(artistCol) ?: "Unknown"
                val data = it.getString(dataCol)
                val duration = it.getLong(durCol)
                songs += Song(id, title, artist, data, duration)
            }
        }
        return songs
    }
}
