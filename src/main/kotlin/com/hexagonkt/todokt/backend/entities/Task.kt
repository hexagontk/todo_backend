package com.hexagonkt.todokt.backend.entities

import com.hexagonkt.settings.SettingsManager

data class Task(
    val id: String,
    val title: String,
    val order: Int? = null,
    val completed: Boolean? = false
){
    val url = "${SettingsManager.requireSetting("serviceUrl") as String}/tasks/$id"
}
