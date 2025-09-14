import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.joalvarez.sendemails"
version = "1.0.0"
description = "Send Emails"

dependencies {
  implementation(project(":shared"))

  // Database
  implementation("org.liquibase:liquibase-core")

  // Documentation
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("openApiVersion")}")


  implementation("org.springframework.boot:spring-boot-starter-mail")
}

tasks.withType<ProcessResources> {
  filesMatching("application.yml") {
    expand(project.properties)
  }
}

tasks.named<BootJar>("bootJar") {
  archiveFileName.set("send-emails.jar")
}
