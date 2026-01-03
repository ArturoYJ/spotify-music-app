package com.example

import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import com.example.infrastructure.dao.DAOFacadeImpl
import com.example.infrastructure.dao.DatabaseFactory
import com.example.infrastructure.routes.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init(environment.config)

    install(ContentNegotiation) {
        json()
    }

    val dao = DAOFacadeImpl()
    configureRouting(dao)
}