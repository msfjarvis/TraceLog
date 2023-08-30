package dev.msfjarvis.tracelog.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

class TraceLogBuildPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.extensions.add(
      TraceLogBuildExtension::class.java,
      "traceLogBuild",
      TraceLogBuildExtensionImpl(target)
    )

    commonKotlinConfiguration(target)
  }

  private fun commonKotlinConfiguration(project: Project) {
    project.tasks.withType<KotlinCompile<*>>().configureEach {
      kotlinOptions.freeCompilerArgs += "-progressive"
    }
  }
}
