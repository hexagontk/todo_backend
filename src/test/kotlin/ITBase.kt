package com.hexagontk.todo.backend.domain

import com.hexagonkt.serialization.SerializationManager
import com.hexagonkt.serialization.jackson.json.Json
import com.hexagontk.todo.backend.application
import com.hexagontk.todo.backend.main
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.testcontainers.containers.MongoDBContainer

@TestInstance(PER_CLASS)
internal open class ITBase {

    private val mongoDb: MongoDBContainer by lazy {
        MongoDBContainer("mongo:7-jammy")
            .withExposedPorts(27017)
            .apply { start() }
    }

    private val mongodbUrl by lazy {
        "mongodb://localhost:${mongoDb.getMappedPort(27017)}/todo"
    }

    @BeforeAll fun startup() {
        SerializationManager.formats = setOf(Json)
        System.setProperty("MONGODB_URL", mongodbUrl)

        main()
    }

    @AfterAll fun shutdown() {
        application.stop()
    }
}
