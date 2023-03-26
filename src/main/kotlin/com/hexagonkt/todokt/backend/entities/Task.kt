package com.hexagonkt.todokt.backend.entities

data class Task(
    val id: String,
    val title: String,
    val order: Int? = null,
    val completed: Boolean? = false
) {
    val url = "${System.getenv("SERVICE_serviceURL")}/tasks/$id"
}
