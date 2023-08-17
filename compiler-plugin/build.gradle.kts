import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ksp)
  alias(libs.plugins.buildconfig)
  `maven-publish`
}

group = "dev.msfjarvis.tracelog"

version = "1.0.0-SNAPSHOT"

kotlin.jvmToolchain(11)

kotlin.explicitApi()

buildConfig {
  packageName("${group}.compiler.plugin")
  useKotlinOutput { internalVisibility = true }
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${project.group}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${project.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${project.version}\"")
}

tasks.test.configure {
  testLogging { events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED) }
  useJUnitPlatform()
}

dependencies {
  compileOnly(libs.kotlin.compiler)
  ksp(libs.auto.ksp)
  compileOnly(libs.auto.annotations)
  compileOnly(projects.annotations)
  testImplementation(projects.annotations)
  testImplementation(libs.kct)
  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.truth) { exclude("org.junit") }
  testCompileOnly(libs.junit.legacy)
}
