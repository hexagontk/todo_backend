package com.hexagonkt.todokt.backend.cucumber

import cucumber.api.java8.En

class Steps : En {
    fun En.Given(title: String) { this.Given(title) {} }
    fun En.When(title: String) { this.Given(title) {} }
    fun En.Then(title: String) { this.Given(title) {} }

    init {
        Given("")
    }
}
