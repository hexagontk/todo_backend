package com.hexagontk.todo.backend.stores

import com.hexagonkt.core.Jvm
import com.hexagonkt.store.mongodb.MongoDbStore
import com.hexagontk.todo.backend.entities.Task

class MongoDbTaskStore: TaskStore {
    private val store = MongoDbStore(
        Task::class,
        Task::id,
        Jvm.systemSetting("MONGODB_URL", "mongodb://root:password@localhost/todo?authSource=admin"),
    )

    override fun findAll(): List<Task> {
        return store.findAll()
    }

    override fun findOne(id: String): Task? {
        return store.findOne(id)
    }

    override fun insertOne(task: Task): String {
        return store.insertOne(task)
    }

    override fun updateOne(id: String, updates: Map<String, Any?>): Boolean {
        return store.updateOne(id, updates)
    }

    override fun deleteAll(): Boolean {
        return store.deleteMany(emptyMap<String, Any>()) > 0
    }

    override fun deleteOne(id: String): Boolean {
        return store.deleteOne(id)
    }
}
