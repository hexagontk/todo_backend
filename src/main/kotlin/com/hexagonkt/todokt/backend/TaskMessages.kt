package com.hexagonkt.todokt.backend

data class TaskRetrievalResponse(
    val url: String,
    val title: String,
    val order: Int?,
    val completed: Boolean?
)

data class TasksRetrievalResponse(
    val tasks: List<TaskRetrievalResponse>
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


data class ErrorResponse(val errors: List<String> = listOf("Unknown error"))

