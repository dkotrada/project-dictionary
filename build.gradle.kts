plugins {
   java
   application
   kotlin("jvm") version "1.4.0"
   kotlin("plugin.serialization") version "1.4.0"
   id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "de.dieterkonrad"
version = "1.0"

application.mainClassName = "AppKt"

repositories {
   mavenCentral()
   jcenter()
}

dependencies {
   implementation(kotlin("stdlib-jdk8"))

   // Frontend
   implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")

   implementation("io.javalin:javalin:3.10.1")
   implementation("io.javalin:javalin-openapi:3.10.1")
   implementation("org.slf4j:slf4j-simple:1.7.30")
   implementation("com.fasterxml.jackson.core:jackson-databind:2.11.2")
   implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")
   implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")

   implementation("org.jetbrains.exposed:exposed:0.17.7")
   implementation("org.postgresql:postgresql:42.2.16")
   implementation("org.mindrot:jbcrypt:0.4")

   // openapi
   implementation("io.swagger.core.v3:swagger-core:2.0.9")
   implementation("org.webjars:swagger-ui:3.24.3")
   implementation("cc.vileda:kotlin-openapi3-dsl:0.20.2")

   // tests
   testImplementation("io.rest-assured:rest-assured:4.3.1")
   testImplementation("io.rest-assured:kotlin-extensions:4.3.1")
   testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
   testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.2")
}

configure<JavaPluginConvention> {
   sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
   compileKotlin {
      kotlinOptions.jvmTarget = "11"
   }
   compileTestKotlin {
      kotlinOptions.jvmTarget = "11"
   }
}

tasks.test {
   useJUnitPlatform()
   testLogging {
      events("passed", "skipped", "failed")
   }
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {}
