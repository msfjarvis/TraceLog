package dev.msfjarvis.tracelog.compiler.plugin

import com.google.auto.service.AutoService
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
@AutoService(CompilerPluginRegistrar::class)
public class TracingCompilerPluginRegistrar : CompilerPluginRegistrar() {

  override val supportsK2: Boolean = true

  override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
    IrGenerationExtension.registerExtension(TracingIrGenerationExtension())
  }
}
