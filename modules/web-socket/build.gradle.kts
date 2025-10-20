import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.joalvarez.websocket"
version = "1.0.0"
description = "Web Socket"

val sockVersion = "1.5.1"
val stompVersion = "2.3.4"

dependencies {
  implementation(project(":shared"))

  // Database
  implementation("org.liquibase:liquibase-core")

  // Documentation
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("openApiVersion")}")

  implementation("org.springframework.boot:spring-boot-starter-websocket")
  implementation("org.webjars:webjars-locator-core")
//  implementation("org.webjars:sockjs-client:${sockVersion}")
  implementation("org.webjars:stomp-websocket:${stompVersion}")
}

tasks.withType<ProcessResources> {
  filesMatching("application.yml") {
    expand(project.properties)
  }
}

tasks.named<BootJar>("bootJar") {
  archiveFileName.set("web-socket.jar")
}
