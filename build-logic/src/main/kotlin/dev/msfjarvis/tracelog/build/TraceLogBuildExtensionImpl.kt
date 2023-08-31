package dev.msfjarvis.tracelog.build

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.plugins.AppliedPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtensionConfig

class TraceLogBuildExtensionImpl(
  private val project: Project,
) : TraceLogBuildExtension {
  override fun publishing() {
    project.pluginManager.apply("com.vanniktech.maven.publish")
    project.pluginManager.apply("org.jetbrains.dokka")
    project.pluginManager.apply("org.jetbrains.kotlinx.binary-compatibility-validator")

    // Published modules should be explicit about their API visibility.
    val kotlinPluginHandler = Action<AppliedPlugin> {
      val kotlin = project.extensions.getByType(
        KotlinTopLevelExtensionConfig::class.java
      )
      kotlin.explicitApi()
    }
    project.pluginManager.withPlugin("org.jetbrains.kotlin.jvm", kotlinPluginHandler)
    project.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform", kotlinPluginHandler)

    project.extensions.getByType<PublishingExtension>().repositories {
      maven {
        name = "Sonatype"
        setUrl {
          if (project.findProperty("VERSION_NAME").toString().endsWith("-SNAPSHOT")) {
            "https://oss.sonatype.org/content/repositories/snapshots/"
          } else {
            val repositoryId =
              System.getenv("SONATYPE_REPOSITORY_ID")
                ?: error("Missing env variable: SONATYPE_REPOSITORY_ID")
            "https://oss.sonatype.org/service/local/staging/deployByRepositoryId/${repositoryId}/"
          }
        }
        credentials {
          username = System.getenv("SONATYPE_USERNAME")
          password = System.getenv("SONATYPE_PASSWORD")
        }
      }
    }
  }

  override fun generateArtifactInfo(basePackage: String) {
    val generateArtifactInfoProvider = project.tasks.register(
      "generateArtifactInfo",
      Copy::class.java,
      ArtifactInfoCopyAction(project, basePackage),
    )
    generateArtifactInfoProvider.configure {
      from(project.rootProject.layout.projectDirectory.dir("artifact-info-template"))
      into(project.layout.buildDirectory.dir("generated/source/artifact-info-template/main"))
    }
    project.extensions.configure<SourceSetContainer>("sourceSets") {
      getByName("main").java.srcDir(generateArtifactInfoProvider)
    }
  }
}
