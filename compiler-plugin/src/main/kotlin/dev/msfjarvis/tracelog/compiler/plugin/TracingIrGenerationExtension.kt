package dev.msfjarvis.tracelog.compiler.plugin

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.types.isNullableAny
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

public class TracingIrGenerationExtension(
    private val messageCollector: MessageCollector,
    private val loggerFunction: String
) : IrGenerationExtension {

  override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
    val debugLogAnnotation =
        pluginContext.referenceClass(
            ClassId(
                FqName("${BuildConfig.KOTLIN_PLUGIN_GROUP}.runtime.annotations"),
                Name.identifier("DebugLog"),
            ))
    if (debugLogAnnotation == null) {
      messageCollector.report(CompilerMessageSeverity.ERROR, "Failed to find 'DebugLog' annotation")
      return
    }
    val loggerFun =
        pluginContext.referenceFunctions(loggerFunction.toCallableId()).firstOrNull {
          val parameters = it.owner.valueParameters
          parameters.size == 1 && parameters[0].type.isNullableAny()
        }
    if (loggerFun == null) {
      messageCollector.report(
          CompilerMessageSeverity.ERROR, "Failed to resolve logger method '$loggerFunction'")
      return
    }
    moduleFragment.transform(
        DebugLogTransformer(pluginContext, debugLogAnnotation, loggerFun), null)
  }

  private fun String.toCallableId(): CallableId {
    val parts = split(".")
    val methodName = parts.last()
    val fqName =
        if (parts.size == 1) {
          FqName("")
        } else {
          FqName((parts - methodName).joinToString("."))
        }
    return CallableId(fqName, Name.identifier(methodName))
  }
}
