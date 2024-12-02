@file:Suppress("UnstableApiUsage")

pluginManagement {
  plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0" }
  repositories {
    includeBuild("build-logic")
    includeBuild("gradle-plugin")
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
        includeModule("com.android.library", "com.android.library.gradle.plugin")
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
        includeModule("com.diffplug.spotless", "com.diffplug.spotless.gradle.plugin")
        includeModule("com.diffplug.spotless", "gradle-plugin")
        includeModule(
          "com.github.gmazzo.buildconfig",
          "com.github.gmazzo.buildconfig.gradle.plugin"
        )
        includeModule("com.github.johnrengelman", "shadow")
        includeModule(
          "com.github.johnrengelman.shadow",
          "com.github.johnrengelman.shadow.gradle.plugin"
        )
        includeModule("com.github.gmazzo.buildconfig", "plugin")
      }
    }
  }
}

dependencyResolutionManagement {
  repositories {
    exclusiveContent {
      forRepository(::google)
      filter {
        includeGroup("com.android.tools")
        includeGroup("com.android.tools.analytics-library")
        includeGroup("com.android.tools.build")
        includeGroup("com.android.tools.ddms")
        includeGroup("com.android.tools.external.com-intellij")
        includeGroup("com.android.tools.external.org-jetbrains")
        includeGroup("com.android.tools.layoutlib")
        includeGroup("com.android.tools.lint")
      }
    }
    mavenLocal { content { includeModule("dev.msfjarvis.tracelog", "compiler-plugin") } }
    mavenCentral()
  }
}

plugins { id("dev.msfjarvis.tracelog.settings") }

rootProject.name = "TraceLog"

include(
  "compiler-plugin",
  "runtime",
  "sample-jvm",
  "sample-kmp",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
