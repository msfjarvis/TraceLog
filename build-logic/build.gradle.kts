plugins { `kotlin-dsl` }

dependencies { implementation(libs.kotlin.gradlePlugin.api) }

kotlin.jvmToolchain(11)

gradlePlugin {
  plugins {
    create("build") {
      id = "dev.msfjarvis.tracelog.build"
      implementationClass = "dev.msfjarvis.tracelog.build.TraceLogBuildPlugin"
    }
    create("settings") {
      id = "dev.msfjarvis.tracelog.settings"
      implementationClass = "dev.msfjarvis.tracelog.build.TraceLogSettingsPlugin"
    }
  }
}
