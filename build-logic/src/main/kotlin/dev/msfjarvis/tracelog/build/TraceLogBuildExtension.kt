package dev.msfjarvis.tracelog.build

interface TraceLogBuildExtension {
  fun publishing()
  fun generateArtifactInfo(basePackage: String)
}
