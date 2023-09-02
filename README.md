# TraceLog ![Maven Central](https://img.shields.io/maven-central/v/dev.msfjarvis.tracelog/dev.msfjarvis.tracelog.gradle.plugin?style=flat-square&label=Latest%20version)

Kotlin compiler plugin to automate `println` debugging, because debuggers are for people smarter than me.

## Installation

Apply the Gradle plugin to your Kotlin project

```kotlin
plugins {
  id("dev.msfjarvis.tracelog") version "0.1.3"
}
```

Optionally configure the annotation class and logger method (defaults shown below)

```kotlin
traceLog {
  loggerFunction.set("kotlin.io.println")
  annotationClass.set("dev/msfjarvis/tracelog/runtime/annotations/DebugLog")
}
```

## Usage

Currently, this prints out a basic textual representation of the method's inputs and execution time. That is,
given this code:

```kotlin
@DebugLog
fun debuggableFunction(p0: String): String {
  return "Debugging is cool!"
}

fun main() {
  debuggableFunction("First parameter")
}
```

The compiler plugin will generate code that writes the following messages

```
⇢ debuggableFunction(p0=First parameter)
⇠ debuggableFunction [214.209us] = Debugging is cool!
```

## Compatibility

| Kotlin Version | TraceLog Version |
|----------------|------------------|
| 1.9.0          | 0.1.x            |
