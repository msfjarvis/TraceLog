import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  id("dev.msfjarvis.tracelog")
}

traceLog { loggerFunction.set("recordMessage") }

fun KotlinNativeTargetWithHostTests.configureTarget() = binaries {
  executable { entryPoint = "main" }
}

kotlin {
  linuxX64 { configureTarget() }
  mingwX64 { configureTarget() }
  macosX64 { configureTarget() }

  dependencies {
    commonMainImplementation(projects.runtime)
    commonMainImplementation(libs.mordant)
  }
}
