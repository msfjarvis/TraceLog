plugins {
  `java-gradle-plugin`
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.buildconfig)
  alias(libs.plugins.ksp)
  `maven-publish`
}

group = "dev.msfjarvis.tracelog"

version = "1.0.0-SNAPSHOT"

kotlin.jvmToolchain(11)

kotlin.explicitApi()

buildConfig {
  val project = projects.compilerPlugin
  packageName("${group}.gradle.plugin")
  useKotlinOutput { internalVisibility = true }
  buildConfigField("String", "KOTLIN_PLUGIN_GROUP", "\"${project.group}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_NAME", "\"${project.name}\"")
  buildConfigField("String", "KOTLIN_PLUGIN_VERSION", "\"${project.version}\"")
}

gradlePlugin.plugins.register("dev.msfjarvis.tracelog") {
  id = group.toString()
  implementationClass = "$group.gradle.plugin.TraceLogGradlePlugin"
}

dependencies {
  compileOnly(libs.kotlin.gradle.plugin)
  ksp(libs.auto.ksp)
  compileOnly(libs.auto.annotations)
}
