package com.hexagontk.todo.backend.domain

import com.hexagonkt.core.media.APPLICATION_JSON
import com.hexagonkt.http.client.jetty.JettyClientAdapter
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.model.NOT_FOUND_404
import com.hexagonkt.rest.bodyObject
import com.hexagonkt.rest.bodyObjects
import com.hexagonkt.rest.tools.StateHttpClient
import com.hexagontk.todo.backend.application
import com.hexagontk.todo.backend.rest.TaskCreationRequest
import com.hexagontk.todo.backend.rest.TaskRetrievalResponse
import org.junit.jupiter.api.Test
import java.util.UUID

internal class RestIT : ITBase() {

    @Test fun `Non existing route returns a 404`() {
        val client = StateHttpClient(
            JettyClientAdapter(),
            "http://localhost:${application.runtimePort()}",
            ContentType(APPLICATION_JSON)
        )

        assert(client.get("/tasks").bodyObjects(::TaskRetrievalResponse).isEmpty())
        client.assertOk()
        client.get("/tasks/${UUID.randomUUID()}")
        client.assertStatus(NOT_FOUND_404)
        client.post("/tasks", TaskCreationRequest("A simple task"))
        var t = client.response.bodyObject(::TaskRetrievalResponse)
        client.assertSuccess()
        client.get(t.url)
        client.assertSuccess()
        assert(client.get("/tasks").bodyObjects(::TaskRetrievalResponse).isNotEmpty())
    }
}
