package dev.msfjarvis.tracelog.compiler.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal val ANNOTATION_NAME =
  CompilerConfigurationKey<String>(
    "Annotation that the compiler plugin looks up to determine which methods need to be transformed"
  )
internal val LOGGER_FUNCTION =
  CompilerConfigurationKey<String>(
    "Logger function invoked to dump function metadata. Must have a single parameter of type `Any?`"
  )

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CommandLineProcessor::class)
public class TracingCommandLineProcessor : CommandLineProcessor {

  internal companion object {
    val OPTION_ANNOTATION_NAME =
      CliOption(
        optionName = "debugAnnotation",
        valueDescription = "${BuildConfig.KOTLIN_PLUGIN_GROUP}.runtime.annotations.DebugLog",
        description = ANNOTATION_NAME.toString(),
        required = true,
        allowMultipleOccurrences = false,
      )
    val OPTION_LOGGER_FUNCTION =
      CliOption(
        optionName = "loggerFunction",
        valueDescription = "kotlin.io.println",
        description = LOGGER_FUNCTION.toString(),
        required = false,
        allowMultipleOccurrences = false
      )
  }

  override val pluginId: String = "dev.msfjarvis.tracelog"
  override val pluginOptions: Collection<AbstractCliOption> =
    listOf(
      OPTION_ANNOTATION_NAME,
      OPTION_LOGGER_FUNCTION,
    )

  override fun processOption(
    option: AbstractCliOption,
    value: String,
    configuration: CompilerConfiguration,
  ) {
    when (option.optionName) {
      OPTION_LOGGER_FUNCTION.optionName -> configuration.put(LOGGER_FUNCTION, value)
      OPTION_ANNOTATION_NAME.optionName -> configuration.put(ANNOTATION_NAME, value)
      else -> super.processOption(option, value, configuration)
    }
  }
}
