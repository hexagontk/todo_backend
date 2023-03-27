package com.hexagonkt.todokt.backend

import com.hexagonkt.http.model.HttpMethod
import com.hexagonkt.http.server.callbacks.CorsCallback
import com.hexagonkt.http.server.handlers.ServerBuilder

fun ServerBuilder.cors() {
    filter(
        "*", CorsCallback(
            allowedMethods = HttpMethod.ALL,
            allowedHeaders = setOf("Content-Type")
        )
    )
}
