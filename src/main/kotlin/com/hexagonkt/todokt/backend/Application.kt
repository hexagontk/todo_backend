package com.hexagonkt.todokt.backend

import com.hexagonkt.http.server.Server
import com.hexagonkt.http.server.ServerPort
import com.hexagonkt.http.server.jetty.JettyServletAdapter
import com.hexagonkt.http.server.servlet.ServletServer
import com.hexagonkt.injection.InjectionManager
import com.hexagonkt.settings.SettingsManager.settings
import com.hexagonkt.todokt.backend.entities.Task
import com.hexagonkt.todokt.backend.stores.MongoDbTaskStore
import com.hexagonkt.todokt.backend.stores.TaskStore
import javax.servlet.annotation.WebListener


internal val injector = InjectionManager {
    bindObject<ServerPort>(JettyServletAdapter())
    bindObject(Task::class, createTaskStore())
}

@WebListener
@Suppress("unused")
class WebApplication : ServletServer(router)

internal val server: Server = Server(injector.inject(), router, settings)

private fun createTaskStore(): TaskStore {
    return MongoDbTaskStore()
}

internal fun main() {
    server.start()
}