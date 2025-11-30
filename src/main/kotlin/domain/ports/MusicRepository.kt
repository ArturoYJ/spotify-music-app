package com.example.domain.ports

import com.example.domain.models.Album
import com.example.domain.models.Artist
import com.example.domain.models.NewAlbum
import com.example.domain.models.NewArtist
import com.example.domain.models.NewSong
import com.example.domain.models.Song

interface MusicRepository {

        suspend fun allArtists(): List<Artist>
        suspend fun artist(id: Int): Artist?
        suspend fun addNewArtist(artist: NewArtist): Artist?
        suspend fun deleteArtist(id: Int): Boolean
        suspend fun editArtist(id: Int, artist: NewArtist): Boolean

        suspend fun allAlbums(): List<Album>
        suspend fun albumsByArtist(artistId: Int): List<Album>
        suspend fun addNewAlbum(album: NewAlbum): Album?
        suspend fun deleteAlbum(id: Int): Boolean
        suspend fun editAlbum(id: Int, album: NewAlbum): Boolean

        suspend fun allSongs(): List<Song>
        suspend fun songsByAlbum(albumId: Int): List<Song>
        suspend fun addNewSong(song: NewSong): Song?
        suspend fun deleteSong(id: Int): Boolean
        suspend fun editSong(id: Int, song: NewSong): Boolean
    }