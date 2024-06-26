package com.hexagontk.todo.backend.rest

import com.hexagonkt.core.*
import com.hexagonkt.serialization.Data
import com.hexagontk.todo.backend.domain.model.Task

data class TaskRetrievalResponse(
    val url: String,
    val title: String,
    val order: Int? = null,
    val completed: Boolean? = null
) : Data<TaskRetrievalResponse> {

    constructor(task: Task) : this(
        url = task.url,
        title = task.title,
        order = task.order,
        completed = task.completed
    )

    constructor(data: Map<String, *>) : this(
        url = data.requireString(TaskRetrievalResponse::url),
        title = data.requireString(TaskCreationRequest::title),
        order = data.getInt(TaskCreationRequest::order),
        completed = data.getBoolean(TaskRetrievalResponse::completed)
    )

    override val data: Map<String, *> =
        fieldsMapOfNotNull(
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
    val order: Int? = null
) : Data<TaskCreationRequest> {

    constructor(data: Map<String, *>) : this(
        title = data.requireString(TaskCreationRequest::title),
        order = data.getInt(TaskCreationRequest::order),
    )

    override val data: Map<String, *> =
        fieldsMapOfNotNull(
            TaskCreationRequest::title to title,
            TaskCreationRequest::order to order,
        )

    override fun copy(data: Map<String, *>): TaskCreationRequest =
        TODO("Not yet implemented")
}

data class TaskUpdateRequest(
    val title: String? = null,
    val order: Int? = null,
    val completed: Boolean? = null
) : Data<TaskUpdateRequest> {

    constructor(data: Map<String, *>) : this(
        title = data.getString(TaskUpdateRequest::title),
        order = data.getInt(TaskUpdateRequest::order),
        completed = data.getBoolean(TaskUpdateRequest::completed),
    )

    override val data: Map<String, *> =
        fieldsMapOfNotNull(
            TaskUpdateRequest::title to title,
            TaskUpdateRequest::order to order,
            TaskUpdateRequest::completed to completed,
        )

    override fun copy(data: Map<String, *>): TaskUpdateRequest =
        copy(
            title = data.getPath(TaskUpdateRequest::title),
            order = data.getPath(TaskUpdateRequest::order),
            completed = data.getPath(TaskUpdateRequest::completed)
        )
}
