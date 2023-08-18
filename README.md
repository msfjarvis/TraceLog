# TraceLog

PoC Kotlin compiler plugin to emit [OpenTelemetry](https://opentelemetry.io/)-compatible logging metadata from Kotlin projects.

Currently, this prints out a basic textual representation of the method's inputs and execution time. That is,
given this code:

```kotlin
@DebugLog
fun debuggableFunction(p0: String): String {
  return "Debugging is cool!"
}
```

The compiler plugin will generate code that writes the following messages

```
⇢ debuggableFunction(p0=First parameter)
⇠ debuggableFunction [214.209us] = Debugging is cool!
```
