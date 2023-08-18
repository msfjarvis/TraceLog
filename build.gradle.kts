plugins { alias(libs.plugins.spotless) }

spotless {
  kotlin {
    target("**/*.kt")
    ktfmt().googleStyle()
  }
  kotlinGradle {
    target("**/*.kts")
    ktfmt().googleStyle()
  }
}
