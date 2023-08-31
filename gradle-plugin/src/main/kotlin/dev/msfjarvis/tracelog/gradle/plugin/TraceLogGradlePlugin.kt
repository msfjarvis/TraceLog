package dev.msfjarvis.tracelog.gradle.plugin

import com.google.auto.service.AutoService
import dev.msfjarvis.tracelog.ArtifactInfo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet.Companion.COMMON_MAIN_SOURCE_SET_NAME
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetContainer
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(Plugin::class)
public class TraceLogGradlePlugin : KotlinCompilerPluginSupportPlugin {

  override fun apply(target: Project) {
    val extension = target.extensions.create<TraceLogExtension>("traceLog")
    extension.loggerFunction.convention("kotlin.io.println")
    extension.annotationClass.convention(ArtifactInfo.DEFAULT_TRACELOG_ANNOTATION)

    target.afterEvaluate {
      val useLocal =
        it.rootProject.findProperty("tracelog.internal.project-dependency").toString().toBoolean()
      val annotationDependency =
        when (extension.annotationClass.get()) {
          ArtifactInfo.DEFAULT_TRACELOG_ANNOTATION ->
            if (useLocal) {
              it.project(":runtime")
            } else {
              "${ArtifactInfo.GROUP}:${ArtifactInfo.RUNTIME_ARTIFACT}:${ArtifactInfo.VERSION}"
            }
          else -> null
        }
      if (annotationDependency != null) {
        if (target.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
          val kotlin = target.extensions.getByName("kotlin") as KotlinSourceSetContainer
          kotlin.sourceSets.getByName(COMMON_MAIN_SOURCE_SET_NAME) { sourceSet ->
            sourceSet.dependencies { implementation(annotationDependency) }
          }
        } else {
          target.dependencies.add(IMPLEMENTATION_CONFIGURATION_NAME, annotationDependency)
        }
      }
    }
  }

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {
    val project = kotlinCompilation.target.project
    val extension = project.extensions.getByType<TraceLogExtension>()

    return kotlinCompilation.target.project.provider {
      buildList {
        add(SubpluginOption(key = "loggerFunction", value = extension.loggerFunction.get()))
        add(SubpluginOption(key = "debugAnnotation", value = extension.annotationClass.get()))
      }
    }
  }

  override fun getCompilerPluginId(): String = ArtifactInfo.GROUP

  override fun getPluginArtifact(): SubpluginArtifact =
    SubpluginArtifact(
      groupId = ArtifactInfo.GROUP,
      artifactId = ArtifactInfo.COMPILER_PLUGIN_ARTIFACT,
      version = ArtifactInfo.VERSION,
    )

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true
}
