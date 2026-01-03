package com.example.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: String,
    val name: String,
    val genre: String?
)

@Serializable
data class NewArtist(
    val name: String,
    val genre: String?
)

@Serializable
data class Album(
    val id: String,
    val title: String,
    val releaseYear: Int?,
    val artistId: String
)

@Serializable
data class NewAlbum(
    val title: String,
    val releaseYear: Int?,
    val artistId: String
)

@Serializable
data class Song(
    val id: String,
    val title: String,
    val duration: Int,
    val albumId: String
)

@Serializable
data class NewSong(
    val title: String,
    val duration: Int,
    val albumId: String
)