package com.hexagonkt.todokt.backend

import com.hexagonkt.http.ALL
import com.hexagonkt.http.Method
import com.hexagonkt.http.server.Router

fun Router.cors(
    origin: String = "*",
    methods: Set<Method> = ALL,
    headers: Set<String> = emptySet(),
    credentials: Boolean = true) {

    before {
        response.setHeader("Access-Control-Allow-Origin", origin)
        response.setHeader("Access-Control-Allow-Credentials", credentials)
        val requestHeaders = if (headers.isEmpty()) request.headers.keys.toSet() else headers
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept")
        response.setHeader("Access-Control-Request-Method", methods.joinToString(","))
    }
}