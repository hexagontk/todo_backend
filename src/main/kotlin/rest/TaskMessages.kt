package com.hexagontk.todo.backend.rest

data class TaskRetrievalResponse(
    val url: String,
    val title: String,
    val order: Int?,
    val completed: Boolean?
)

data class TaskCreationRequest(
    val title: String,
    val order: Int?
)

data class TaskUpdateRequest(
    val title: String?,
    val order: Int?,
    val completed: Boolean?
)
