plugins {
  alias(libs.plugins.kotlin.jvm)
  id("dev.msfjarvis.tracelog")
  application
}

kotlin.jvmToolchain(11)

traceLog { loggerFunction.set("dev.msfjarvis.tracelog.sample.recordMessage") }

application { mainClass.set("dev.msfjarvis.tracelog.sample.MainKt") }

dependencies {
  implementation(projects.runtime)
  implementation(libs.mordant)
}
