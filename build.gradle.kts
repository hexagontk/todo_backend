import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

plugins {
    kotlin("jvm") version("2.0.0")
    id("org.graalvm.buildtools.native") version("0.10.2")
}

val hexagonVersion = "3.5.3"
val hexagonExtraVersion = "3.5.3"
val gradleScripts = "https://raw.githubusercontent.com/hexagontk/hexagon/$hexagonVersion/gradle"

ext.set("options", "-Xms64M -Xmx1G -XX:+UseNUMA")
ext.set("applicationClass", "com.hexagonkt.todokt.backend.ApplicationKt")

apply(from = "$gradleScripts/kotlin.gradle")
apply(from = "$gradleScripts/application.gradle")
apply(from = "$gradleScripts/native.gradle")

dependencies {
    implementation("com.hexagonkt:http_server_jetty:$hexagonVersion")
    implementation("com.hexagonkt:serialization_jackson_json:$hexagonVersion")
    implementation("com.hexagonkt.extra:store_mongodb:$hexagonExtraVersion")
    implementation("com.hexagonkt.extra:converters:$hexagonExtraVersion")
}

tasks.wrapper {
    gradleVersion = "8.8"
    distributionType = ALL
}
