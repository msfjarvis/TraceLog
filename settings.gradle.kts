@file:Suppress("UnstableApiUsage")

pluginManagement {
  repositories {
    includeBuild("build-logic")
    mavenCentral()
    gradlePluginPortal()
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
