package dev.msfjarvis.tracelog.build

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class TraceLogSettingsPlugin : Plugin<Settings> {
  override fun apply(target: Settings) {
    target.gradle.allprojects {
      pluginManager.apply("dev.msfjarvis.tracelog.build")
    }
  }
}
