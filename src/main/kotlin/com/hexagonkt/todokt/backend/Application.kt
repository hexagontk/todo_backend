package com.hexagonkt.todokt.backend

import com.hexagonkt.converters.ConvertersManager
import com.hexagonkt.core.getPath
import com.hexagonkt.core.requirePath
import com.hexagonkt.http.server.HttpServer
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.todokt.backend.entities.Task
import com.hexagonkt.todokt.backend.stores.MongoDbTaskStore


internal class Application {
    private val server: HttpServer = HttpServer(
        adapter = JettyServletAdapter(minThreads = 4),
        handler = Router(store = MongoDbTaskStore()).handler
    )

    fun start() {
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
        ConvertersManager.register(Task::class to TaskRetrievalResponse::class) { task ->
            TaskRetrievalResponse(
                url = task.url,
                title = task.title,
                order = task.order,
                completed = task.completed
            )
        }
        ConvertersManager.register(Map::class to TaskCreationRequest::class) { map ->
            TaskCreationRequest(
                title = map.requirePath(TaskCreationRequest::title),
                order = map.getPath(TaskCreationRequest::order),
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
