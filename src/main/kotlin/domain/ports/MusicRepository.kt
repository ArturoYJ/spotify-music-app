package com.example.domain.ports

import com.example.domain.models.*

interface MusicRepository {
    suspend fun allArtists(): List<Artist>
    suspend fun artist(id: String): Artist?
    suspend fun addNewArtist(artist: NewArtist): Artist?
    suspend fun deleteArtist(id: String): Boolean
    suspend fun editArtist(id: String, artist: NewArtist): Boolean

    suspend fun allAlbums(): List<Album>
    suspend fun albumsByArtist(artistId: String): List<Album>
    suspend fun addNewAlbum(album: NewAlbum): Album?
    suspend fun deleteAlbum(id: String): Boolean
    suspend fun editAlbum(id: String, album: NewAlbum): Boolean

    suspend fun allSongs(): List<Song>
    suspend fun songsByAlbum(albumId: String): List<Song>
    suspend fun addNewSong(song: NewSong): Song?
    suspend fun deleteSong(id: String): Boolean
    suspend fun editSong(id: String, song: NewSong): Boolean
}