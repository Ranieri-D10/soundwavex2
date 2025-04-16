package com.smartchoicehub.soundwavex.data.model

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String,
    val duration: Long,
)