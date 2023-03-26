package com.hexagonkt.todokt.backend

import com.hexagonkt.todokt.backend.entities.Task

data class TaskRetrievalResponse(
    val url: String,
    val title: String,
    val order: Int?,
    val completed: Boolean?
) {
    companion object {
        fun of(task: Task) = TaskRetrievalResponse(
            url = task.url,
            title = task.title,
            order = task.order,
            completed = task.completed
        )
    }
}

data class TaskCreationRequest(val data: Map<String, *>) {
    val title: String by data
    val order: Int? by data
}

data class TaskUpdateRequest(val data: Map<String, *>) {
    val title: String? by data
    val order: Int? by data
    val completed: Boolean? by data
}

