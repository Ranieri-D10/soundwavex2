package com.smartchoicehub.soundwavex.data.model

data class Playlist(
    val name: String,
    val songs: MutableList<Song>
)