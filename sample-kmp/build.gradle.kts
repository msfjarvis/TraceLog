import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

plugins { alias(libs.plugins.kotlin.multiplatform) }

tasks.withType<KotlinCompile<KotlinCommonOptions>>().configureEach {
  kotlinOptions.options.freeCompilerArgs.addAll(
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
    kotlinCompilerPluginClasspath(projects.compilerPlugin)
    kotlinNativeCompilerPluginClasspath(projects.compilerPlugin)
    commonMainImplementation(projects.runtime)
    commonMainImplementation(libs.mordant)
  }
}
