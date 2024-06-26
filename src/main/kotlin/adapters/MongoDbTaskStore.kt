package com.hexagontk.todo.backend.adapters

import com.hexagonkt.core.Jvm
import com.hexagonkt.core.getPath
import com.hexagonkt.core.requirePath
import com.hexagonkt.store.mongodb.MongoDbStore
import com.hexagontk.todo.backend.domain.model.Task
import com.hexagontk.todo.backend.domain.TaskStore

class MongoDbTaskStore: TaskStore {
    private val store = MongoDbStore(
        Task::class,
        Task::id,
        MongoDbStore.database(
            Jvm.systemSetting(
                "MONGODB_URL", "mongodb://root:password@localhost/todo?authSource=admin"
            )
        ),
        encoder = {
            mapOf(
                Task::id.name to it.id,
                Task::title.name to it.title,
                Task::order.name to it.order,
                Task::url.name to it.url,
                Task::completed.name to it.completed
            )
        },
        decoder = {
            Task(
                id = it.requirePath(Task::id),
                title = it.requirePath(Task::title),
                order = it.getPath(Task::order),
                completed = it.getPath(Task::completed)
            )
        },
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
