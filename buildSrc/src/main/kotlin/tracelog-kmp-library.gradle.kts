plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.library")
  `maven-publish`
}

group = "dev.msfjarvis.tracelog"

version = "1.0.0-SNAPSHOT"

kotlin {
  jvmToolchain(11)
  explicitApi()
  jvm()
  ios()
  androidTarget()
}
