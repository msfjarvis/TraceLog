import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  application
}

application {
  mainClass.set("dev.msfjarvis.tracelog.sample.MainKt")
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
  implementation(libs.mordant)
}
