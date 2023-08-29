plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.library")
  id("tracelog-common")
}

kotlin {
  jvmToolchain(11)
  explicitApi()
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
