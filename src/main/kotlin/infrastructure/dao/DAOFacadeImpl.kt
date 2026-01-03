package com.example.infrastructure.dao

import com.example.domain.models.*
import com.example.domain.ports.MusicRepository
import org.jetbrains.exposed.sql.* // Necesario para select y deleteWhere
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.UUID

class DAOFacadeImpl : MusicRepository {

    private fun rowToArtist(row: ResultRow) = Artist(
        id = row[Artists.id].toString(),
        name = row[Artists.name],
        genre = row[Artists.genre]
    )
    private fun rowToAlbum(row: ResultRow) = Album(
        id = row[Albums.id].toString(),
        title = row[Albums.title],
        releaseYear = row[Albums.releaseYear],
        artistId = row[Albums.artistId].toString()
    )
    private fun rowToSong(row: ResultRow) = Song(
        id = row[Songs.id].toString(),
        title = row[Songs.title],
        duration = row[Songs.duration],
        albumId = row[Songs.albumId].toString()
    )
    private fun String.toUUID(): UUID? = runCatching { UUID.fromString(this) }.getOrNull()

    override suspend fun allArtists(): List<Artist> = DatabaseFactory.dbQuery {
        Artists.selectAll().map(::rowToArtist)
    }
    override suspend fun artist(id: String): Artist? = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery null
        Artists.selectAll().where { Artists.id eq uuid }.map(::rowToArtist).singleOrNull()
    }
    override suspend fun addNewArtist(artist: NewArtist): Artist? = DatabaseFactory.dbQuery {
        val insert = Artists.insert {
            it[name] = artist.name
            it[genre] = artist.genre
        }
        insert.resultedValues?.singleOrNull()?.let(::rowToArtist)
    }

    // --- DELETE PROTEGIDO (Nivel Aplicación) ---
    override suspend fun deleteArtist(id: String): Boolean = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery false

        // 1. EL ESCUDO: Preguntamos si hay álbumes de este artista
        val numeroDeAlbumes = Albums.selectAll().where { Albums.artistId eq uuid }.count()

        if (numeroDeAlbumes > 0) {
            // Si hay álbumes, bloqueamos la operación nosotros mismos
            println("PROTECCIÓN: No se puede borrar artista $id porque tiene $numeroDeAlbumes álbumes.")
            return@dbQuery false
        }

        // 2. Si llegamos aquí, es seguro borrar (la BD tiene CASCADE, pero no hay nada que llevarse en cascada)
        Artists.deleteWhere { Artists.id eq uuid } > 0
    }

    override suspend fun editArtist(id: String, artist: NewArtist): Boolean = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery false
        Artists.update({ Artists.id eq uuid }) {
            it[name] = artist.name
            it[genre] = artist.genre
        } > 0
    }

    override suspend fun allAlbums(): List<Album> = DatabaseFactory.dbQuery {
        Albums.selectAll().map(::rowToAlbum)
    }
    override suspend fun albumsByArtist(artistId: String): List<Album> = DatabaseFactory.dbQuery {
        val uuid = artistId.toUUID() ?: return@dbQuery emptyList()
        Albums.selectAll().where { Albums.artistId eq uuid }.map(::rowToAlbum)
    }
    override suspend fun addNewAlbum(album: NewAlbum): Album? = DatabaseFactory.dbQuery {
        val artistUuid = album.artistId.toUUID() ?: return@dbQuery null
        val insert = Albums.insert {
            it[title] = album.title
            it[releaseYear] = album.releaseYear
            it[artistId] = artistUuid
        }
        insert.resultedValues?.singleOrNull()?.let(::rowToAlbum)
    }

    override suspend fun deleteAlbum(id: String): Boolean = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery false

        // 1. EL ESCUDO: Preguntamos si hay canciones en este álbum
        val numeroDeCanciones = Songs.selectAll().where { Songs.albumId eq uuid }.count()

        if (numeroDeCanciones > 0) {
            println("PROTECCIÓN: No se puede borrar álbum $id porque tiene $numeroDeCanciones canciones.")
            return@dbQuery false
        }

        Albums.deleteWhere { Albums.id eq uuid } > 0
    }

    override suspend fun editAlbum(id: String, album: NewAlbum): Boolean = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery false
        val artistUuid = album.artistId.toUUID() ?: return@dbQuery false
        Albums.update({ Albums.id eq uuid }) {
            it[title] = album.title
            it[releaseYear] = album.releaseYear
            it[artistId] = artistUuid
        } > 0
    }

    override suspend fun allSongs(): List<Song> = DatabaseFactory.dbQuery { Songs.selectAll().map(::rowToSong) }
    override suspend fun songsByAlbum(albumId: String): List<Song> = DatabaseFactory.dbQuery {
        val uuid = albumId.toUUID() ?: return@dbQuery emptyList()
        Songs.selectAll().where { Songs.albumId eq uuid }.map(::rowToSong)
    }
    override suspend fun addNewSong(song: NewSong): Song? = DatabaseFactory.dbQuery {
        val albumUuid = song.albumId.toUUID() ?: return@dbQuery null
        val insert = Songs.insert {
            it[title] = song.title
            it[duration] = song.duration
            it[albumId] = albumUuid
        }
        insert.resultedValues?.singleOrNull()?.let(::rowToSong)
    }
    override suspend fun deleteSong(id: String): Boolean = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery false
        Songs.deleteWhere { Songs.id eq uuid } > 0
    }
    override suspend fun editSong(id: String, song: NewSong): Boolean = DatabaseFactory.dbQuery {
        val uuid = id.toUUID() ?: return@dbQuery false
        val albumUuid = song.albumId.toUUID() ?: return@dbQuery false
        Songs.update({ Songs.id eq uuid }) {
            it[title] = song.title
            it[duration] = song.duration
            it[albumId] = albumUuid
        } > 0
    }
}