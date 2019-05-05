package com.hexagonkt.todokt.backend.cucumber

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = [
        "html:build/reports/cucumber",
        "json:build/reports/cucumber/cucumber.json"
    ],
    features = [ "src/test/resources/features" ]
)
class CucumberTest
