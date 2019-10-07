package com.hexagonkt.todokt.backend

import com.hexagonkt.http.server.Router

fun Router.cors() {

    before {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Headers", "Content-Type")
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PATCH,DELETE,OPTIONS")
    }
}
