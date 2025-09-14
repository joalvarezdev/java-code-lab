import org.gradle.kotlin.dsl.*
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
  id("java-library")
  id("org.springframework.boot") version "3.5.2"
  id("io.spring.dependency-management") version "1.1.6"
  id("co.uzzu.dotenv.gradle") version "4.0.0"
  id("pmd")
}

val instancioVersion = "5.4.1"
val javaVersion = 21
val pmdVersion = "7.16.0"

allprojects {
  apply(plugin = "pmd")

  repositories {
    mavenCentral()
  }
  
  group = "com.joalvarez"
  version = "1.0.0"

  pmd {
    toolVersion = pmdVersion
    ruleSets = listOf()
    ruleSetFiles = files("${rootProject.projectDir}/linters/pmd-ruleset.xml")
    isConsoleOutput = true
    isIgnoreFailures = false
  }
}

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "io.spring.dependency-management")
  apply(plugin = "org.springframework.boot")

  // Configuración común de Java
  configure<JavaPluginExtension> {
    toolchain {
      languageVersion = JavaLanguageVersion.of(javaVersion)
    }
  }

  tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
  }

  tasks.withType<Javadoc> {
	  options.encoding = "UTF-8"
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  dependencies {
    // Spring Boot básico
    "api"("org.springframework.boot:spring-boot-starter")
    "api"("org.springframework.boot:spring-boot-starter-data-jpa")
    "api"("org.springframework.boot:spring-boot-starter-web")

    // implementation("org.springframework.boot:spring-boot-starter-actuator")

    // implementation("com.h2database:h2")

    // implementation("org.liquibase:liquibase-core")

    // Testing común
    "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    "testImplementation"("org.instancio:instancio-junit:${instancioVersion}")

    // implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${openApiVersion}")

    // implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.4")
  }

  if (name != "shared") {

    dependencies {
      "runtimeOnly"("org.postgresql:postgresql")
    }

  }

}