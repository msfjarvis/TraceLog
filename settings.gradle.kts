@file:Suppress("UnstableApiUsage")

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    exclusiveContent {
      forRepository { google() }
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
    mavenCentral()
  }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0" }

rootProject.name = "TraceLog"

include(
  "compiler-plugin",
  "gradle-plugin",
  "runtime",
  "sample-jvm",
  "sample-kmp",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
