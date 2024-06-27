package com.hexagontk.todo.backend.rest.messages

import com.hexagonkt.core.fieldsMapOfNotNull
import com.hexagonkt.core.getBoolean
import com.hexagonkt.core.getInt
import com.hexagonkt.core.getString
import com.hexagonkt.serialization.Data

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
        TODO("Not implemented")
}
