@file:Suppress("UnstableApiUsage")

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement { repositories { mavenCentral() } }

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0" }

rootProject.name = "tracelog"

include(
    "annotations",
    "compiler-plugin",
    "gradle-plugin",
)

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
