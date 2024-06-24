package com.hexagontk.todo.backend.rest

import com.hexagonkt.core.*
import com.hexagonkt.serialization.Data
import com.hexagontk.todo.backend.domain.model.Task

data class TaskRetrievalResponse(
    val url: String,
    val title: String,
    val order: Int?,
    val completed: Boolean?
) : Data<TaskRetrievalResponse> {

    constructor(task: Task) : this(
        url = task.url,
        title = task.title,
        order = task.order,
        completed = task.completed
    )

    override val data: Map<String, *> = fieldsMapOfNotNull(
        TaskRetrievalResponse::url to url,
        TaskRetrievalResponse::title to title,
        TaskRetrievalResponse::order to order,
        TaskRetrievalResponse::completed to completed,
    )

    override fun copy(data: Map<String, *>): TaskRetrievalResponse =
        copy(
            url = data.getString(TaskRetrievalResponse::url) ?: url,
            title = data.getString(TaskRetrievalResponse::title) ?: title,
            order = data.getInt(TaskRetrievalResponse::order) ?: order,
            completed = data.getBoolean(TaskRetrievalResponse::completed) ?: completed
        )
}

data class TaskCreationRequest(
    val title: String,
    val order: Int?
) : Data<TaskCreationRequest> {

    constructor(data: Map<String, *>) : this(
        title = data.requireString(TaskCreationRequest::title),
        order = data.getInt(TaskCreationRequest::order),
    )

    override val data: Map<String, *>
        get() = TODO("Not yet implemented")

    override fun copy(data: Map<String, *>): TaskCreationRequest {
        TODO("Not yet implemented")
    }
}

data class TaskUpdateRequest(
    val title: String?,
    val order: Int?,
    val completed: Boolean?
)
