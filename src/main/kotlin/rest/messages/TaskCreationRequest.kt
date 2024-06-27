package com.hexagontk.todo.backend.rest.messages

import com.hexagonkt.core.fieldsMapOfNotNull
import com.hexagonkt.core.getInt
import com.hexagonkt.core.requireString
import com.hexagonkt.serialization.Data

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
        TODO("Not implemented")
}
