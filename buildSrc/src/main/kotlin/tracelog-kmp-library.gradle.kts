plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.library")
  id("tracelog-common")
}

kotlin {
  jvmToolchain(11)
  explicitApi()
  jvm()
  ios()
  androidTarget()
}
