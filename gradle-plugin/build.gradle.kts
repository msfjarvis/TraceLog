plugins {
  id("tracelog-jvm-library")
  `java-gradle-plugin`
  alias(libs.plugins.buildconfig)
  alias(libs.plugins.ksp)
}

buildConfig {
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
  implementation(platform(embeddedKotlin("bom")))
  compileOnly(libs.auto.annotations)
  compileOnly(libs.kotlin.gradlePlugin.api)
  compileOnly(libs.kotlin.stdlib)
  ksp(libs.auto.ksp)
}
