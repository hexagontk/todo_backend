package com.hexagontk.todo.backend.domain

import com.hexagontk.todo.backend.domain.model.Task

interface TaskStore {

    fun findAll(): List<Task>

    fun findOne(id: String): Task?

    fun insertOne(task: Task): String

    fun updateOne(id: String, updates: Map<String, Any?>): Boolean

    fun deleteAll(): Boolean

    fun deleteOne(id: String): Boolean

}
