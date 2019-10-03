package com.hexagonkt.todokt.backend

import java.time.LocalDateTime

data class TaskRetrievalResponse(
    val url: String,
    val title: String,
    val order: Int?,
    val completed: Boolean?
)

data class TaskRetrievalResponseRoot(val task: TaskRetrievalResponse)

data class TasksRetrievalResponse(
    val tasks: List<TaskRetrievalResponse>
)


data class TaskCreationRequest(
    val title: String,
    val order: Int?
)

data class TaskCreationRequestRoot(val task: TaskCreationRequest)

data class TaskUpdateRequest(
    val title: String?,
    val order: Int?,
    val completed: Boolean?
)

data class TaskUpdateRequestRoot(val task: TaskUpdateRequest)


data class ErrorResponse(val errors: List<String> = listOf("Unknown error"))

