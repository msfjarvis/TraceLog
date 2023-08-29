plugins {
  alias(libs.plugins.spotless)
  id("tracelog-jvm-library") apply false
  id("tracelog-kmp-library") apply false
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/")
    ktfmt().googleStyle()
  }
  kotlinGradle {
    target("**/*.kts")
    targetExclude("**/build/")
    ktfmt().googleStyle()
  }
}
