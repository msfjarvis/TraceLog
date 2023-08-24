package dev.msfjarvis.tracelog.runtime

public interface TracingConsumer {
  public fun trace(
    timestamp: Long,
    traceId: String?,
    spanId: String?,
    traceFlags: Byte,
  )
}
