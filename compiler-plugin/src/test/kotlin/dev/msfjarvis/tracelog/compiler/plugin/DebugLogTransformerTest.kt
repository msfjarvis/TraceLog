package dev.msfjarvis.tracelog.compiler.plugin

import com.google.common.truth.Truth.assertThat
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.KotlinCompilation.ExitCode
import com.tschuchort.compiletesting.PluginOption
import com.tschuchort.compiletesting.SourceFile.Companion.kotlin
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCompilerApi::class)
class DebugLogTransformerTest {

  @Test
  fun `compiler plugin successfully transforms code`() {
    val annotationSrcFile =
      kotlin(
        "DebugLog.kt",
        """
      package dev.msfjarvis.annotation
      annotation class DebugLog
    """
          .trimIndent()
      )
    val srcFile =
      kotlin(
        "SourceFile.kt",
        """
      import dev.msfjarvis.annotation.DebugLog
      val messages = mutableListOf<String>()
      fun recordMessage(message: Any?) {
        messages += message.toString()
      }
      
      @DebugLog
      fun transformable() {
        println("In a transformable function!")
      }
      
      @DebugLog
      fun transformableWithReturnValue(): String {
        println("In a transformable function!")
        return "Return value!"
      }
      
      fun nonTransformable() {
        println("Not in a transformable function!")
      }
      
      class TracingTest {
        @DebugLog
        fun transformableInClass() {
          println("In a transformable function!")
        }
        
        fun nonTransformable() {
          println("Not in a transformable function!")
        }
      }
    """
          .trimIndent()
      )

    val result =
      KotlinCompilation()
        .apply {
          val processor = TracingCommandLineProcessor()
          pluginOptions = buildList {
            add(
              processor.option(TracingCommandLineProcessor.OPTION_LOGGER_FUNCTION, "recordMessage")
            )
            add(
              processor.option(
                TracingCommandLineProcessor.OPTION_ANNOTATION_NAME,
                "dev/msfjarvis/annotation/DebugLog"
              )
            )
          }
          sources = listOf(annotationSrcFile, srcFile)
          compilerPluginRegistrars = listOf(TracingCompilerPluginRegistrar())
          commandLineProcessors = listOf(processor)
          noOptimize = true

          inheritClassPath = true
          messageOutputStream = System.out
        }
        .compile()
    assertThat(result.exitCode).isEqualTo(ExitCode.OK)

    val kClazz = result.classLoader.loadClass("SourceFileKt")
    val transformableWithReturnValue =
      kClazz.declaredMethods.first { it.name == "transformableWithReturnValue" }
    val retVal = transformableWithReturnValue.invoke(null)
    assertThat(retVal).isInstanceOf(String::class.java)
    assertThat(retVal).isEqualTo("Return value!")
    val msgField = kClazz.declaredFields.first { it.name == "messages" }
    msgField.isAccessible = true
    val messages = msgField.get(null) as? List<*>
    assertThat(messages).isNotNull()
    assertThat(messages).contains("⇢ transformableWithReturnValue()")
    assertThat((messages?.last() as? String)?.replace("\\[.*]".toRegex(), "[]"))
      .isEqualTo(
        "⇠ transformableWithReturnValue [] = Return value!",
      )
  }

  @Test
  fun `nested logger function`() {
    val annotationSrcFile =
      kotlin(
        "DebugLog.kt",
        """
      package dev.msfjarvis.annotation
      annotation class DebugLog
    """
          .trimIndent()
      )
    val srcFile =
      kotlin(
        "SourceFile.kt",
        """
      import dev.msfjarvis.annotation.DebugLog
      
      @DebugLog
      fun transformable() {
        println("In a transformable function!")
      }
      
      @DebugLog
      fun transformableWithReturnValue(): String {
        println("In a transformable function!")
        return "Return value!"
      }
      
      fun nonTransformable() {
        println("Not in a transformable function!")
      }
      
      class TracingTest {
        @DebugLog
        fun transformableInClass() {
          println("In a transformable function!")
        }
        
        fun nonTransformable() {
          println("Not in a transformable function!")
        }
      }
    """
          .trimIndent()
      )
    val loggerFile =
      kotlin(
        "Logger.kt",
        """
      package com.example
      object Logger {
        val messages = mutableListOf<String>()
      
        @JvmStatic
        fun recordMessage(message: Any?) {
          messages += message.toString()
        }
      }
    """
          .trimIndent()
      )

    val result =
      KotlinCompilation()
        .apply {
          val processor = TracingCommandLineProcessor()
          pluginOptions = buildList {
            add(
              processor.option(
                TracingCommandLineProcessor.OPTION_LOGGER_FUNCTION,
                "com.example.Logger.recordMessage"
              )
            )
            add(
              processor.option(
                TracingCommandLineProcessor.OPTION_ANNOTATION_NAME,
                "dev/msfjarvis/annotation/DebugLog"
              )
            )
          }
          sources = listOf(annotationSrcFile, srcFile, loggerFile)
          compilerPluginRegistrars = listOf(TracingCompilerPluginRegistrar())
          commandLineProcessors = listOf(processor)
          noOptimize = true

          inheritClassPath = true
          messageOutputStream = System.out
        }
        .compile()
    assertThat(result.exitCode).isEqualTo(ExitCode.OK)

    val kClazz = result.classLoader.loadClass("SourceFileKt")
    val transformableWithReturnValue =
      kClazz.declaredMethods.first { it.name == "transformableWithReturnValue" }
    val retVal = transformableWithReturnValue.invoke(null)
    assertThat(retVal).isInstanceOf(String::class.java)
    assertThat(retVal).isEqualTo("Return value!")

    val loggerClazz = result.classLoader.loadClass("com.example.Logger")
    val msgField = loggerClazz.declaredFields.first { it.name == "messages" }
    msgField.isAccessible = true
    val messages = msgField.get(null) as? List<*>
    assertThat(messages).isNotNull()
    assertThat(messages).contains("⇢ transformableWithReturnValue()")
    assertThat((messages?.last() as? String)?.replace("\\[.*]".toRegex(), "[]"))
      .isEqualTo(
        "⇠ transformableWithReturnValue [] = Return value!",
      )
  }

  private fun CommandLineProcessor.option(key: CliOption, value: Any?): PluginOption {
    return PluginOption(pluginId, key.optionName, value.toString())
  }
}
