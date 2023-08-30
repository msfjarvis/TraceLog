plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
}

traceLogBuild { publishing() }

kotlin {
  jvmToolchain(11)
  jvm()
  androidTarget()
  iosX64()
  iosArm64()
  tvosX64()
  tvosArm64()
  watchosX64()
  watchosArm32()
  watchosArm64()
  macosX64()
  macosArm64()
  iosSimulatorArm64()
  watchosSimulatorArm64()
  tvosSimulatorArm64()
  mingwX64()
  linuxX64()
  androidNativeArm32()
  androidNativeArm64()
}

android {
  compileSdk = 33
  namespace = "dev.msfjarvis.tracelog"
}
