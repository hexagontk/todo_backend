package com.hexagonkt.todokt.backend.stores

import com.hexagonkt.todokt.backend.entities.Task

interface TaskStore {

    fun findAll(): List<Task>

    fun findOne(id: String): Task?

    fun insertOne(task: Task): String

    fun updateOne(id: String, updates: Map<String, Any?>): Boolean

    fun deleteAll(): Boolean

    fun deleteOne(id: String): Boolean

}
