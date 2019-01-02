package com.shiming.hement;

/** Not a real crash reporting library!  不是一个真正的崩溃报告库！
 *  把日志写到本地的操作
 *
 */
public final class FakeCrashLibrary {
  public static void log(int priority, String tag, String message) {
    // TODO add log entry to circular buffer.
  }

  public static void logWarning(Throwable t) {
    // TODO report non-fatal warning.
  }

  public static void logError(Throwable t) {
    // TODO report non-fatal error.
  }

  private FakeCrashLibrary() {
    throw new AssertionError("No instances.");

  }
}
