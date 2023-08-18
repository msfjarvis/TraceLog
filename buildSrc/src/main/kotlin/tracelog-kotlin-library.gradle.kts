plugins {
  id("org.jetbrains.kotlin.jvm")
  `maven-publish`
}

group = "dev.msfjarvis.tracelog"
version = "1.0.0-SNAPSHOT"

kotlin {
  jvmToolchain(11)
  explicitApi()
}
