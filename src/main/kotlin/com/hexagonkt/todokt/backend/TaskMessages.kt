package com.hexagonkt.todokt.backend

import java.time.LocalDateTime

data class TaskResponse(
    val name: String
)


data class TaskCreationRequest(
    val name: String,
    val labels: List<String>?,
    val priority: TaskPriority?
)

data class TaskCreationRequestRoot(val task: TaskCreationRequest)

data class TaskCreationResponse(
    val id: Long,
    val name: String,
    val labels: List<String>,
    val priority: TaskPriority?,
    val createdAt: LocalDateTime
)

data class TaskCreationResponseRoot(val taskResponse: TaskCreationResponse)

