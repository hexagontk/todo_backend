package com.hexagontk.todo.backend.rest.messages

import com.hexagonkt.core.fieldsMapOfNotNull
import com.hexagonkt.core.getBoolean
import com.hexagonkt.core.getInt
import com.hexagonkt.core.requireString
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
        TODO("Not implemented")
}
