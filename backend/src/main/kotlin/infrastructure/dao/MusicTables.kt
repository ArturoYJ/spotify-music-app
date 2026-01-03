package com.example.infrastructure.dao

import org.jetbrains.exposed.sql.Table

object Artists : Table("artistas") {
    val id = uuid("id").autoGenerate()
    val name = varchar("name", 100)
    val genre = varchar("genre", 50).nullable()
    override val primaryKey = PrimaryKey(id)
}

object Albums : Table("albumes") {
    val id = uuid("id").autoGenerate()
    val title = varchar("title", 150)
    val releaseYear = integer("release_year").nullable()
    val artistId = uuid("artist_id").references(Artists.id)
    override val primaryKey = PrimaryKey(id)
}

object Songs : Table("tracks") {
    val id = uuid("id").autoGenerate()
    val title = varchar("title", 150)
    val duration = integer("duration")
    val albumId = uuid("album_id").references(Albums.id)
    override val primaryKey = PrimaryKey(id)
}