package com.hexagonkt.todokt.backend

import com.hexagonkt.converters.ConvertersManager
import com.hexagonkt.core.keys
import com.hexagonkt.core.requireKeys
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
                id = map.requireKeys(Task::id),
                title = map.requireKeys(Task::title),
                order = map.keys(Task::order),
                completed = map.keys(Task::completed)
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
                title = map.requireKeys(TaskCreationRequest::title),
                order = map.keys(TaskCreationRequest::order),
            )
        }
        ConvertersManager.register(Map::class to TaskUpdateRequest::class) { map ->
            TaskUpdateRequest(
                title = map.keys(TaskUpdateRequest::title),
                order = map.keys(TaskUpdateRequest::order),
                completed = map.keys(TaskUpdateRequest::completed)
            )
        }
    }
}

internal fun main() {
    Application().start()
}
