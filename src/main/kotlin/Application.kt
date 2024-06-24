package com.hexagontk.todo.backend

import com.hexagonkt.converters.ConvertersManager
import com.hexagonkt.core.*
import com.hexagonkt.http.server.HttpServer
import com.hexagonkt.http.server.HttpServerSettings
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.serialization.SerializationManager
import com.hexagonkt.serialization.jackson.json.Json
import com.hexagontk.todo.backend.domain.model.Task
import com.hexagontk.todo.backend.adapters.MongoDbTaskStore
import com.hexagontk.todo.backend.rest.Router
import com.hexagontk.todo.backend.rest.TaskUpdateRequest

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
        registerConverters()
        server.start()
    }

    private fun registerConverters() {
        ConvertersManager.register(Task::class to Map::class) { task ->
            mapOf(
                Task::id.name to task.id,
                Task::title.name to task.title,
                Task::order.name to task.order,
                Task::url.name to task.url,
                Task::completed.name to task.completed
            )
        }
        ConvertersManager.register(Map::class to Task::class) { map ->
            Task(
                id = map.requirePath(Task::id),
                title = map.requirePath(Task::title),
                order = map.getPath(Task::order),
                completed = map.getPath(Task::completed)
            )
        }
        ConvertersManager.register(Map::class to TaskUpdateRequest::class) { map ->
            TaskUpdateRequest(
                title = map.getPath(TaskUpdateRequest::title),
                order = map.getPath(TaskUpdateRequest::order),
                completed = map.getPath(TaskUpdateRequest::completed)
            )
        }
    }
}

internal fun main() {
    Application().start()
}
