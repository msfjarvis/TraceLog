import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("tracelog-jvm-library")
  application
}

application { mainClass.set("dev.msfjarvis.tracelog.sample.MainKt") }

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.freeCompilerArgs.addAll(
    "-P",
    "plugin:dev.msfjarvis.tracelog:loggerFunction=dev.msfjarvis.tracelog.sample.recordMessage",
    "-P",
    "plugin:dev.msfjarvis.tracelog:debugAnnotation=dev/msfjarvis/tracelog/runtime/annotations/DebugLog",
  )
}

dependencies {
  kotlinCompilerPluginClasspath(projects.compilerPlugin)
  implementation(projects.runtime)
  implementation(libs.mordant)
}
