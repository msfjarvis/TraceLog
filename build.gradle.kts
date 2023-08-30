plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.dokka) apply false
  alias(libs.plugins.kotlinx.binaryCompatibilityValidator) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.mavenPublish) apply false
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/", "**/artifact-info-template/*")
    ktfmt().googleStyle()
  }
  kotlinGradle {
    target("**/*.kts")
    targetExclude("**/build/")
    ktfmt().googleStyle()
  }
}
