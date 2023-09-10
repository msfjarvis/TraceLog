package dev.msfjarvis.tracelog.compiler.plugin

import com.google.auto.service.AutoService
import dev.msfjarvis.tracelog.ArtifactInfo
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
public class TracingCompilerPluginRegistrar : CompilerPluginRegistrar() {

  override val supportsK2: Boolean = true

  override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
    val messageCollector =
      configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
    val loggerFunction = configuration[LOGGER_FUNCTION] ?: "kotlin.io.println"
    val annotationName = configuration[ANNOTATION_NAME] ?: ArtifactInfo.DEFAULT_TRACELOG_ANNOTATION

    IrGenerationExtension.registerExtension(
      TracingIrGenerationExtension(messageCollector, loggerFunction, annotationName)
    )
  }
}
