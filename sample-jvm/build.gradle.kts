import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  embeddedKotlin("jvm")
  application
}

group = "dev.msfjarvis.tracelog"
version = "1.0.0-SNAPSHOT"

application {
  mainClass = "dev.msfjarvis.tracelog.sample.MainKt"
}

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.freeCompilerArgs.addAll(
    "-P",
    "plugin:dev.msfjarvis.tracelog:loggerFunction=dev.msfjarvis.tracelog.sample.recordMessage",
  )
}


dependencies {
  kotlinCompilerPluginClasspath(projects.compilerPlugin)
  implementation(projects.annotations)
}
