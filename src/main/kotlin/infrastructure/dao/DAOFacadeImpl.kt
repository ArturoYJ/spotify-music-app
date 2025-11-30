package com.example.infrastructure.dao

import com.example.domain.models.Album
import com.example.domain.models.Artist
import com.example.domain.models.NewAlbum
import com.example.domain.models.NewArtist
import com.example.domain.models.NewSong
import com.example.domain.models.Song
import com.example.domain.ports.MusicRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : MusicRepository {

    private fun rowToArtist(row: ResultRow) = Artist(
        id = row[Artists.id], name = row[Artists.name], bio = row[Artists.bio], imageUrl = row[Artists.imageUrl]
    )
    private fun rowToAlbum(row: ResultRow) = Album(
        id = row[Albums.id],
        name = row[Albums.name],
        year = row[Albums.year],
        coverUrl = row[Albums.coverUrl],
        artistId = row[Albums.artistId]
    )
    private fun rowToSong(row: ResultRow) = Song(
        id = row[Songs.id],
        title = row[Songs.title],
        durationSeconds = row[Songs.durationSeconds],
        songUrl = row[Songs.songUrl],
        albumId = row[Songs.albumId]
    )

    override suspend fun allArtists(): List<Artist> = DatabaseFactory.dbQuery {
        Artists.selectAll().map(::rowToArtist)
    }
    override suspend fun artist(id: Int): Artist? = DatabaseFactory.dbQuery {
        Artists.selectAll().where { Artists.id eq id }
            .map(::rowToArtist).singleOrNull()
    }
    override suspend fun addNewArtist(artist: NewArtist): Artist? = DatabaseFactory.dbQuery {
        val insert = Artists.insert {
            it[name] = artist.name
            it[bio] = artist.bio
            it[imageUrl] = artist.imageUrl
        }
        insert.resultedValues?.singleOrNull()?.let(::rowToArtist)
    }
    override suspend fun deleteArtist(id: Int): Boolean = DatabaseFactory.dbQuery {
        Artists.deleteWhere { Artists.id eq id } > 0
    }
    override suspend fun editArtist(id: Int, artist: NewArtist):Boolean = DatabaseFactory.dbQuery {
        Artists.update({ Artists.id eq id }) {
            it[name] = artist.name
            it[bio] = artist.bio
            it[imageUrl] = artist.imageUrl
        } > 0
    }

    override suspend fun allAlbums(): List<Album> = DatabaseFactory.dbQuery {
        Albums.selectAll().map(::rowToAlbum)
    }
    override suspend fun albumsByArtist(artistId: Int): List<Album> = DatabaseFactory.dbQuery {
        Albums.selectAll().where { Albums.artistId eq artistId }
            .map(::rowToAlbum)
    }
    override suspend fun addNewAlbum(album: NewAlbum): Album? = DatabaseFactory.dbQuery {
        val insert = Albums.insert {
            it[name] = album.name
            it[year] = album.year
            it[coverUrl] = album.coverUrl
            it[artistId] = album.artistId
        }
        insert.resultedValues?.singleOrNull()?.let(::rowToAlbum)
    }
    override suspend fun deleteAlbum(id: Int): Boolean = DatabaseFactory.dbQuery {
        Albums.deleteWhere { Albums.id eq id } > 0
    }
    override suspend fun editAlbum(id: Int, album: NewAlbum): Boolean = DatabaseFactory.dbQuery {
        Albums.update( { Albums.id eq id }) {
            it[name] = album.name
            it[year] = album.year
            it[coverUrl] = album.coverUrl
            it[artistId] = album.artistId
        } > 0
    }

    override suspend fun allSongs(): List<Song> = DatabaseFactory.dbQuery {
        Songs.selectAll().map(::rowToSong)
    }
    override suspend fun songsByAlbum(albumId: Int): List<Song> = DatabaseFactory.dbQuery {
        Songs.selectAll().where { Songs.albumId eq albumId }
            .map(::rowToSong)
    }
    override suspend fun addNewSong(song: NewSong): Song? = DatabaseFactory.dbQuery {
        val insert = Songs.insert {
            it[title] = song.title
            it[durationSeconds] = song.durationSeconds
            it[songUrl] = song.songUrl
            it[albumId] = song.albumId
        }
        insert.resultedValues?.singleOrNull()?.let(::rowToSong)
    }
    override suspend fun deleteSong(id: Int): Boolean = DatabaseFactory.dbQuery {
        Songs.deleteWhere { Songs.id eq id } > 0
    }
    override suspend fun editSong(id: Int, song: NewSong): Boolean = DatabaseFactory.dbQuery {
        Songs.update( { Songs.id eq id} ) {
            it[title] = song.title
            it[durationSeconds] = song.durationSeconds
            it[songUrl] = song.songUrl
            it[albumId] = song.albumId
        } > 0
    }
}