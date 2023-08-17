plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.kotlin.jvm) apply false
}

spotless {
  kotlin {
    target("**/*.kt")
    ktfmt()
  }
  kotlinGradle {
    target("**/*.kts")
    ktfmt()
  }
}
