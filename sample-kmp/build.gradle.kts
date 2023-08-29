import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { kotlin("multiplatform") }

tasks.withType<KotlinCompile>().configureEach {
  compilerOptions.freeCompilerArgs.addAll(
    "-P",
    "plugin:dev.msfjarvis.tracelog:loggerFunction=recordMessage",
    "-P",
    "plugin:dev.msfjarvis.tracelog:debugAnnotation=dev/msfjarvis/tracelog/runtime/annotations/DebugLog",
  )
}

fun KotlinNativeTargetWithHostTests.configureTarget() = binaries {
  executable { entryPoint = "main" }
}

kotlin {
  linuxX64 { configureTarget() }
  mingwX64 { configureTarget() }
  macosX64 { configureTarget() }

  dependencies {
    commonMainImplementation(platform(embeddedKotlin("bom")))
    kotlinCompilerPluginClasspath(projects.compilerPlugin)
    commonMainImplementation(projects.runtime)
    commonMainImplementation(libs.mordant)
  }
}
