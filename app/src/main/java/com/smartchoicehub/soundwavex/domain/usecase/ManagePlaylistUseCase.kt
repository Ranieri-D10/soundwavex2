package com.smartchoicehub.soundwavex.domain.usecase

import com.smartchoicehub.soundwavex.data.model.Playlist
import com.smartchoicehub.soundwavex.data.model.Song

class ManagePlaylistUseCase {

    fun addSongToPlaylist(song: Song, playlist: Playlist) {
        if (!playlist.songs.contains(song)) {
            playlist.songs.add(song)
        }
    }

    fun removeSongFromPlaylist(song: Song, playlist: Playlist) {
        playlist.songs.remove(song)
    }

    fun createPlaylist(name: String): Playlist {
        return Playlist(name = name, songs = mutableListOf())
    }
}
