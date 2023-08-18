@file:Suppress("UnstableApiUsage")

pluginManagement {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories { mavenCentral() }
  versionCatalogs {
    maybeCreate("libs").apply {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}

rootProject.name = "buildSrc"

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
