package com.example.infrastructure.routes

import com.example.domain.models.NewAlbum
import com.example.domain.models.NewArtist
import com.example.domain.models.NewSong
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.domain.ports.MusicRepository

fun Application.configureRouting(dao: MusicRepository) {
    routing {

        route("/artists") {

            get { call.respond(dao.allArtists()) }
            post {
                val form = call.receive<NewArtist>()
                val created = dao.addNewArtist(form)
                if (created != null) call.respond(HttpStatusCode.Created, created)
                else call.respond(HttpStatusCode.BadRequest)
            }
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val artist = id?.let { dao.artist(it) }
                if (artist != null) call.respond(artist) else call.respond(HttpStatusCode.NotFound)
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val deleted = id?.let { dao.deleteArtist(it) } ?: false
                if (deleted) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound)
            }
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID nulo")
                    return@put
                }

                try {

                    val form = call.receive<NewArtist>()

                    val edited = dao.editArtist(id, form)

                    if (edited) {
                        call.respond(HttpStatusCode.OK, "Artista actualizado")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "No existe el artista $id")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error en el json: ${e.message}")
                }
            }
        }

        route("/albums") {
            get { call.respond(dao.allAlbums()) }
            post {
                val form = call.receive<NewAlbum>()
                val created = dao.addNewAlbum(form)
                call.respond(HttpStatusCode.Created, created ?: "error")
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null && dao.deleteAlbum(id)) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
            put ( "/{id}" ){
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID nulo")
                    return@put
                }
                try {
                    val form = call.receive<NewAlbum>()

                    val edited = dao.editAlbum(id, form)
                    if (edited) {
                        call.respond(HttpStatusCode.OK, "Album actualizado")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "No existe el album $id")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error con json: ${e.message}")
                }
            }
        }

        route("/songs") {
            get { call.respond(dao.allSongs()) }
            post {
                val form = call.receive<NewSong>()
                val created = dao.addNewSong(form)
                call.respond(HttpStatusCode.Created, created ?: "error")
            }
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id != null && dao.deleteSong(id)) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
            put ( "/{id}" ){
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID nulo")
                    return@put
                }

                try {

                    val form = call.receive<NewSong>()
                    val edited = dao.editSong(id, form)

                    if (edited) {
                        call.respond(HttpStatusCode.OK, "Canción actualizada")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "No existe la canción $id")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Error con json: ${e.message}")
                }
            }
            }
    }
}