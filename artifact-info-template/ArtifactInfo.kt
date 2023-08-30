package $basePackage

internal object ArtifactInfo {
    const val GROUP = "$publishGroup"
    const val VERSION = "$publishVersion"

    const val COMPILER_PLUGIN_ARTIFACT = "compiler-plugin"
    const val GRADLE_PLUGIN_ARTIFACT = "gradle-plugin"
    const val RUNTIME_ARTIFACT = "runtime"

    const val DEFAULT_TRACELOG_ENABLED = true
    const val DEFAULT_TRACELOG_ANNOTATION = "dev/msfjarvis/tracelog/runtime/annotations/DebugLog"
}
