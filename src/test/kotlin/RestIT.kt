package com.hexagontk.todo.backend.domain

import com.hexagonkt.core.media.APPLICATION_JSON
import com.hexagonkt.http.client.jetty.JettyClientAdapter
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.model.NOT_FOUND_404
import com.hexagonkt.rest.bodyObject
import com.hexagonkt.rest.bodyObjects
import com.hexagonkt.rest.tools.StateHttpClient
import com.hexagontk.todo.backend.application
import com.hexagontk.todo.backend.rest.messages.TaskCreationRequest
import com.hexagontk.todo.backend.rest.messages.TaskRetrievalResponse
import com.hexagontk.todo.backend.rest.messages.TaskUpdateRequest
import org.junit.jupiter.api.Test
import java.util.UUID
import kotlin.test.assertEquals

internal class RestIT : ITBase() {

    @Test fun `Non existing route returns a 404`() {
        val client = StateHttpClient(
            JettyClientAdapter(),
            "http://localhost:${application.runtimePort()}",
            ContentType(APPLICATION_JSON)
        )

        assert(client.get("/tasks").bodyObjects(::TaskRetrievalResponse).isEmpty())
        client.assertOk()

        val uuid = UUID.randomUUID()
        client.get("/tasks/$uuid")
        client.assertStatus(NOT_FOUND_404)
        client.assertBody("Task with id $uuid not found")

        client.post("/tasks", TaskCreationRequest("A simple task"))
        client.assertOk()
        val newTask = client.response.bodyObject(::TaskRetrievalResponse)
        assertEquals("A simple task", newTask.title)

        val retrievedTask = client.get(newTask.url).bodyObject(::TaskRetrievalResponse)
        client.assertOk()
        assertEquals("A simple task", retrievedTask.title)

        assertEquals(1, client.get("/tasks").bodyObjects(::TaskRetrievalResponse).size)

        client.patch(retrievedTask.url, TaskUpdateRequest("Modified task"))
        val modifiedTask = client.response.bodyObject(::TaskRetrievalResponse)
        client.assertOk()
        assertEquals("Modified task", modifiedTask.title)

        client.delete(modifiedTask.url)
        client.assertOk()
        assertEquals("", client.response.bodyString())

        client.get(modifiedTask.url)
        client.assertStatus(NOT_FOUND_404)
        client.assertBodyContains("Task with id", "not found")

        assert(client.get("/tasks").bodyObjects(::TaskRetrievalResponse).isEmpty())
        client.assertOk()
    }
}
