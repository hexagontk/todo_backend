package com.hexagontk.todo.backend

import com.hexagonkt.core.*
import com.hexagonkt.http.server.HttpServer
import com.hexagonkt.http.server.HttpServerSettings
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.SerializationManager
import com.hexagonkt.serialization.jackson.json.Json
import com.hexagontk.todo.backend.adapters.MongoDbTaskStore
import com.hexagontk.todo.backend.rest.Router

internal class Application {
    private val server: HttpServer = HttpServer(
        adapter = JettyServletAdapter(minThreads = 4),
        handler = Router(store = MongoDbTaskStore()).handler,
        settings = HttpServerSettings(
            bindPort = 2010,
            bindAddress = Jvm.systemSetting("BIND_ADDRESS", LOOPBACK_INTERFACE)
        ),
    )

    fun start() {
        SerializationManager.formats = setOf(Json)
        server.start()
    }

    fun stop() {
        server.stop()
    }

    fun runtimePort(): Int =
        server.runtimePort
}
