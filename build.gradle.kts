import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

plugins {
    kotlin("jvm") version("2.0.0")
    id("org.graalvm.buildtools.native") version("0.10.2")
}

val hexagonVersion = "3.6.0"
val hexagonExtraVersion = "3.6.0"
val logbackVersion = "1.5.6"
val gradleScripts = "https://raw.githubusercontent.com/hexagontk/hexagon/$hexagonVersion/gradle"

ext.set("modules", "java.xml,java.naming")
ext.set("options", "-Xms32M -Xmx128m -XX:+UseNUMA")
ext.set("applicationClass", "com.hexagontk.todo.backend.ApplicationKt")

apply(from = "$gradleScripts/kotlin.gradle")
apply(from = "$gradleScripts/application.gradle")

dependencies {
    implementation("com.hexagonkt:http_server_jetty:$hexagonVersion")
    implementation("com.hexagonkt:serialization_jackson_json:$hexagonVersion")
    implementation("com.hexagonkt:rest:$hexagonVersion")
    implementation("com.hexagonkt.extra:store_mongodb:$hexagonExtraVersion")
    implementation("com.hexagonkt.extra:converters:$hexagonExtraVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
}

tasks.wrapper {
    gradleVersion = "8.8"
    distributionType = ALL
}

tasks.register("buildImage", type = Exec::class) {
    dependsOn("jpackage")
    commandLine("docker", "compose", "--profile", "local", "build")

    environment(
        "REGISTRY" to (environment["REGISTRY"] ?: "k3d.localhost:5000/"),
        "VERSION" to (environment["VERSION"] ?: version)
    )
}

tasks.register("pushImage", type = Exec::class) {
    dependsOn("jpackage")
    commandLine("docker", "compose", "--profile", "local", "push")

    environment(
        "REGISTRY" to (environment["REGISTRY"] ?: "k3d.localhost:5000/"),
        "VERSION" to (environment["VERSION"] ?: version)
    )
}

tasks.register("up", type = Exec::class) {
    dependsOn("buildImage")
    commandLine("docker", "compose", "--profile", "local", "up")
}
