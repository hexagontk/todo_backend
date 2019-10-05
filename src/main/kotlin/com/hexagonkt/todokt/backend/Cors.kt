package com.hexagonkt.todokt.backend

import com.hexagonkt.http.ALL
import com.hexagonkt.http.Method
import com.hexagonkt.http.server.Router

fun Router.cors() {

    before {
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Headers", "Content-Type")
        response.setHeader("Access-Control-Request-Method", "GET,POST,PATCH,DELETE")
    }
}