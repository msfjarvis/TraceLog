package dev.msfjarvis.tracelog.build

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class ArtifactInfoCopyAction(
  private val project: Project,
  private val basePackage: String,
) : Action<Copy> {

  override fun execute(t: Copy) {
    t.expand(
      mapOf(
        "basePackage" to basePackage,
        "publishGroup" to "${project.rootProject.findProperty("GROUP")}",
        "publishVersion" to "${project.rootProject.findProperty("VERSION_NAME")}",
      )
    )
    t.filteringCharset = "UTF-8"
  }
}
