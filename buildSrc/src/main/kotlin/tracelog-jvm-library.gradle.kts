plugins {
  id("org.jetbrains.kotlin.jvm")
  id("tracelog-common")
}

kotlin {
  jvmToolchain(11)
  explicitApi()
}
