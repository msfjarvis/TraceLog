package dev.msfjarvis.tracelog.gradle.plugin

import org.gradle.api.provider.Property

public abstract class TraceLogExtension {

  public abstract val annotationClass: Property<String>

  public abstract val loggerFunction: Property<String>
}
