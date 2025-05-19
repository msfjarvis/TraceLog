@file:Suppress("UnstableApiUsage")

pluginManagement {
  plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }
  repositories {
    includeBuild("../build-logic")
    mavenCentral()
    exclusiveContent {
      forRepository(::google)
      filter {
        includeGroup("androidx.databinding")
        includeGroup("com.android")
        includeGroup("com.android.tools.analytics-library")
        includeGroup("com.android.tools.build")
        includeGroup("com.android.tools.build.jetifier")
        includeGroup("com.android.databinding")
        includeGroup("com.android.tools.ddms")
        includeGroup("com.android.tools.layoutlib")
        includeGroup("com.android.tools.lint")
        includeGroup("com.android.tools.utp")
        includeGroup("com.google.testing.platform")
        includeModule("com.android.tools", "annotations")
        includeModule("com.android.tools", "common")
        includeModule("com.android.tools", "desugar_jdk_libs")
        includeModule("com.android.tools", "desugar_jdk_libs_configuration")
        includeModule("com.android.tools", "dvlib")
        includeModule("com.android.tools", "play-sdk-proto")
        includeModule("com.android.tools", "repository")
        includeModule("com.android.tools", "sdklib")
        includeModule("com.android.tools", "sdk-common")
      }
    }
    exclusiveContent {
      forRepository(::gradlePluginPortal)
      filter {
        includeModule(
          "com.github.gmazzo.buildconfig",
          "com.github.gmazzo.buildconfig.gradle.plugin",
        )
        includeModule("com.github.gmazzo.buildconfig", "plugin")
      }
    }
  }
}

dependencyResolutionManagement {
  repositories { mavenCentral() }
  versionCatalogs { create("libs") { from(files("../gradle/libs.versions.toml")) } }
}

plugins { id("dev.msfjarvis.tracelog.settings") }

rootProject.name = "TraceLog-Gradle-Plugin"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
