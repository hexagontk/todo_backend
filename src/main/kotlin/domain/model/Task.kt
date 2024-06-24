package com.hexagontk.todo.backend.domain.model

data class Task(
    val id: String,
    val title: String,
    val order: Int? = null,
    val completed: Boolean? = false
) {
//    val url = "${System.getenv("SERVICE_URL")}/tasks/$id"
    val url = "/tasks/$id"
}
