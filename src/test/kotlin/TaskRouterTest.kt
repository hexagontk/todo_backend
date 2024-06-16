package com.hexagontk.todo.backend.entities

import com.hexagontk.todo.backend.stores.TaskStore

internal class TaskRouterTest {

    val store = object : TaskStore {
        val taskList = mutableListOf<Task>()

        override fun findAll(): List<Task> {
            return taskList
        }

        override fun findOne(id: String): Task? {
            return taskList.find { it.id == id }
        }

        override fun insertOne(task: Task): String {
            taskList.add(task)
            return task.id
        }

        override fun updateOne(id: String, updates: Map<String, Any?>): Boolean {
            val index = taskList.indexOf(taskList.find { it.id == id })
            val task = taskList.removeAt(index)

            taskList.add(
                Task(
                    id = task.id,
                    title = updates[Task::title.name]?.let { updates[Task::title.name] as String } ?: task.title,
                    order = updates[Task::order.name]?.let { updates[Task::order.name] as Int } ?: task.order,
                    completed = updates[Task::completed.name]?.let { updates[Task::completed.name] as Boolean } ?: task.completed
                )
            )

            return true
        }

        override fun deleteAll(): Boolean {
            return taskList.removeAll(emptyList())
        }

        override fun deleteOne(id: String): Boolean {
            return taskList.removeIf{ it.id == id }
        }
    }
}
