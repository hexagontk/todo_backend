package com.hexagontk.todo.backend.rest

import com.hexagonkt.converters.convert
import com.hexagonkt.core.media.APPLICATION_JSON
import com.hexagonkt.core.media.TEXT_PLAIN
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.handlers.HttpContext
import com.hexagonkt.http.handlers.HttpController
import com.hexagonkt.http.handlers.HttpHandler
import com.hexagonkt.http.handlers.path
import com.hexagonkt.http.model.HttpMethod.*
import com.hexagonkt.http.server.callbacks.CorsCallback
import com.hexagonkt.http.server.callbacks.UrlCallback
import com.hexagonkt.serialization.jackson.json.Json
import com.hexagonkt.serialization.parseMap
import com.hexagonkt.serialization.serialize
import com.hexagontk.todo.backend.domain.model.Task
import com.hexagontk.todo.backend.domain.TaskStore
import java.util.UUID

class Router(private val store: TaskStore) : HttpController {

    private val json = ContentType(APPLICATION_JSON)
    private val text = ContentType(TEXT_PLAIN)

    private val tasksHandler: HttpHandler = path("/tasks") {
        get {
            val tasks = store.findAll()
            val taskResponses = tasks.map { task -> task.convert(TaskRetrievalResponse::class) }

            ok(taskResponses.serialize(Json), contentType = json)
        }

        get("/{id}") {
            val id = pathParameters.getValue("id")
            getTask(id, store)
        }

        post {
            val bodyString = request.bodyString()
            val taskCreationRequest = bodyString.parseMap(Json).convert(TaskCreationRequest::class)
            val task = Task(
                id = UUID.randomUUID().toString(),
                title = taskCreationRequest.title,
                order = taskCreationRequest.order
            )
            store.insertOne(task)
            getTask(task.id, store)
        }

        patch("/{id}") {
            val id = pathParameters.getValue("id")
            val bodyString = request.bodyString()
            val taskUpdateRequest = bodyString.parseMap(Json).convert(TaskUpdateRequest::class)
            store.findOne(id) ?: notFound("Task with id $id not found")
            val updates = mapOf(
                Task::title.name to taskUpdateRequest.title,
                Task::order.name to taskUpdateRequest.order,
                Task::completed.name to taskUpdateRequest.completed
            )
            if (store.updateOne(id, updates)) getTask(id, store)
            else badRequest("Unable to update task with id $id", contentType = text)
        }

        delete {
            store.deleteAll()
            ok()
        }

        delete("/{id}") {
            val id = pathParameters.getValue("id")
            store.findOne(id) ?: notFound("Task with id $id not found")
            if (store.deleteOne(id)) ok()
            else badRequest("Unable to delete task with id $id", contentType = text)
        }
    }

    override val handler: HttpHandler = path {
        filter(
            "*",
            CorsCallback(
                allowedMethods = setOf(GET, POST, PATCH, DELETE, OPTIONS),
                allowedHeaders = setOf("Content-Type")
            )
        )

        get(callback = UrlCallback("classpath:static/index.html"))

        use(tasksHandler)
    }

    private fun HttpContext.getTask(id: String, store: TaskStore): HttpContext {
        val task = store.findOne(id)?.convert(TaskRetrievalResponse::class)
        return if (task != null) ok(task.serialize(Json), contentType = json)
        else notFound("Task with id $id not found", contentType = text)
    }
}
