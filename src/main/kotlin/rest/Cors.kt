package com.hexagontk.todo.backend.rest

import com.hexagonkt.http.model.HttpMethod.DELETE
import com.hexagonkt.http.model.HttpMethod.GET
import com.hexagonkt.http.model.HttpMethod.OPTIONS
import com.hexagonkt.http.model.HttpMethod.PATCH
import com.hexagonkt.http.model.HttpMethod.POST
import com.hexagonkt.http.server.callbacks.CorsCallback
import com.hexagonkt.http.handlers.HandlerBuilder

fun HandlerBuilder.cors() {
    filter(
        "*",
        CorsCallback(
            allowedMethods = setOf(GET, POST, PATCH, DELETE, OPTIONS),
            allowedHeaders = setOf("Content-Type")
        )
    )
}
