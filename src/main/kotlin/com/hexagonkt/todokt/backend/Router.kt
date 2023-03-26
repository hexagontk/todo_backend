package com.hexagonkt.todokt.backend

import com.hexagonkt.core.media.APPLICATION_JSON
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.server.handlers.HttpServerContext
import com.hexagonkt.http.server.handlers.path
import com.hexagonkt.serialization.jackson.json.Json
import com.hexagonkt.serialization.parseMap
import com.hexagonkt.serialization.serialize
import com.hexagonkt.todokt.backend.entities.Task
import com.hexagonkt.todokt.backend.stores.TaskStore
import java.util.UUID


class Router(private val store: TaskStore) {
    private val tasksHandler = path("/tasks") {
        get {
            val tasks = store.findAll()

            val taskResponse = tasks.map {
                TaskRetrievalResponse(
                    url = it.url,
                    title = it.title,
                    order = it.order,
                    completed = it.completed
                )
            }

            ok(taskResponse)
        }

        get("/{id}") {
            val id = pathParameters.getValue("id")
            getTask(id, store)
        }

        post {
            val taskCreationRequest = TaskCreationRequest(
                request.bodyString().parseMap(Json).mapKeys { it.key.toString() }
            )
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
            val taskUpdateRequest = TaskUpdateRequest(
                request.bodyString().parseMap(Json).mapKeys { it.key.toString() }
            )
            store.findOne(id) ?: notFound("Task with id $id not found")

            val updates = mapOf(
                Task::title.name to taskUpdateRequest.title,
                Task::order.name to taskUpdateRequest.order,
                Task::completed.name to taskUpdateRequest.completed
            )

            if (store.updateOne(id, updates)) getTask(id, store)
            else badRequest("Unable to update task with id $id")
        }

        delete {
            store.deleteAll()
            ok()
        }

        delete("/{id}") {
            val id = pathParameters.getValue("id")

            store.findOne(id) ?: notFound("Task with id $id not found")

            if (store.deleteOne(id)) ok()
            else badRequest("Unable to delete task with id $id")
        }
    }

    val handler = path {
        // TODO: CORS
        //cors()

        // Needed to allow for OPTION requests for all endpoints
        // TODO: Add options
        // options("/*")
        after("*") { send(contentType = ContentType(APPLICATION_JSON)) }
        after("*") {
            val serialized = response.body.serialize(Json)
            send(body = serialized)
        }
        use(tasksHandler)
    }
}

internal fun HttpServerContext.getTask(id: String, store: TaskStore): HttpServerContext {
    val task = store.findOne(id)?.let { TaskRetrievalResponse.of(it) }
    return if (task != null) {
        ok(task)
    } else {
        notFound("Task with id $id not found")
    }
}
