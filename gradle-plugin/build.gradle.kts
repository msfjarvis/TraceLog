@file:Suppress("UnstableApiUsage")

import java.util.Properties

plugins {
  alias(libs.plugins.kotlin.jvm)
  `java-gradle-plugin`
  alias(libs.plugins.ksp)
  alias(libs.plugins.mavenPublish).apply(false)
  alias(libs.plugins.dokka).apply(false)
  alias(libs.plugins.kotlinx.binaryCompatibilityValidator).apply(false)
}

kotlin.jvmToolchain(11)

fun loadParentProperties() {
  val properties = Properties()
  file("../gradle.properties").inputStream().use { properties.load(it) }

  properties.forEach { (k, v) ->
    val key = k.toString()
    val value = providers.gradleProperty(name).getOrElse(v.toString())
    extra.set(key, value)
  }
}

loadParentProperties()

traceLogBuild {
  publishing()
  generateArtifactInfo("dev.msfjarvis.tracelog")
}

gradlePlugin.plugins.register("dev.msfjarvis.tracelog") {
  id = properties["GROUP"].toString()
  implementationClass = "${properties["GROUP"]}.gradle.plugin.TraceLogGradlePlugin"
}

dependencies {
  compileOnly(gradleKotlinDsl())
  compileOnly(libs.auto.annotations)
  compileOnly(libs.kotlin.gradlePlugin.api)
  compileOnly(libs.kotlin.stdlib)
  ksp(libs.auto.ksp)
}
