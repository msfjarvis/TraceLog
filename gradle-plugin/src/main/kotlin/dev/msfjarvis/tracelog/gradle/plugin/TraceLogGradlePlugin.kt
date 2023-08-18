package dev.msfjarvis.tracelog.gradle.plugin

import com.google.auto.service.AutoService
import org.gradle.api.Plugin
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

@AutoService(Plugin::class)
public class TraceLogGradlePlugin : KotlinCompilerPluginSupportPlugin {

  override fun applyToCompilation(
    kotlinCompilation: KotlinCompilation<*>
  ): Provider<List<SubpluginOption>> {
    return kotlinCompilation.target.project.provider {
      buildList { add(SubpluginOption(key = "loggerFunction", value = "kotlin.io.println")) }
    }
  }

  override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_GROUP

  override fun getPluginArtifact(): SubpluginArtifact =
    SubpluginArtifact(
      groupId = BuildConfig.KOTLIN_PLUGIN_GROUP,
      artifactId = BuildConfig.KOTLIN_PLUGIN_NAME,
      version = BuildConfig.KOTLIN_PLUGIN_VERSION,
    )

  override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true
}
