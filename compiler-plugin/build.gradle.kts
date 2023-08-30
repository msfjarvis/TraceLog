import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.ksp)
}

kotlin.jvmToolchain(11)

traceLogBuild {
  publishing()
  generateArtifactInfo("dev.msfjarvis.tracelog")
}

tasks.test.configure {
  testLogging { events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED) }
  useJUnitPlatform()
}

dependencies {
  compileOnly(libs.kotlin.compiler)
  ksp(libs.auto.ksp)
  compileOnly(libs.auto.annotations)
  testImplementation(libs.kct)
  testImplementation(libs.junit.jupiter.api)
  testImplementation(libs.junit.jupiter.engine)
  testImplementation(libs.truth) { exclude("org.junit") }
  testCompileOnly(libs.junit.legacy)
}
