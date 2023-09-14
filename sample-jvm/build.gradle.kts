import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.shadow)
  application
}

kotlin.jvmToolchain(11)

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.freeCompilerArgs.addAll(
    "-P",
    "plugin:dev.msfjarvis.tracelog:loggerFunction=dev.msfjarvis.tracelog.sample.recordMessage",
    "-P",
    "plugin:dev.msfjarvis.tracelog:debugAnnotation=dev/msfjarvis/tracelog/runtime/annotations/DebugLog",
  )
}

application { mainClass.set("dev.msfjarvis.tracelog.sample.MainKt") }

dependencies {
  kotlinCompilerPluginClasspath(projects.compilerPlugin)
  implementation(projects.runtime)
  implementation(libs.mordant)
}
