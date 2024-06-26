package com.hexagontk.todo.backend.domain.model

data class Task(
    val id: String,
    val title: String,
    val order: Int? = null,
    val completed: Boolean? = false
) {
    val url = "/tasks/$id"
}
