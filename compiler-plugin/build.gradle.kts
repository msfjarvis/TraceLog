import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  id("tracelog-jvm-library")
  alias(libs.plugins.ksp)
  alias(libs.plugins.buildconfig)
}

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
  implementation(platform(embeddedKotlin("bom")))
  compileOnly(libs.kotlin.compiler)
  ksp(libs.auto.ksp)
  compileOnly(libs.auto.annotations)
  testImplementation(libs.kct)
  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.truth) { exclude("org.junit") }
  testCompileOnly(libs.junit.legacy)
}
