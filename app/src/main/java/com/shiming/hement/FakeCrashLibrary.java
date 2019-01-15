package com.shiming.hement;

import com.orhanobut.logger.Logger;

/** Not a real crash reporting library!  不是一个真正的崩溃报告库！
 *  把日志写到本地的操作
 *
 */
public final class FakeCrashLibrary {

  /**
   * 将日志项添加到循环缓冲区。
   * @param priority
   * @param tag
   * @param message
   */
  public static void log(int priority, String tag, String message) {

  }

  /**
   * 报告非致命警告。
   * @param t
   */
  public static void logWarning(Throwable t) {

  }

  /**
   * 报告非致命错误。
   * @param t
   */
  public static void logError(Throwable t) {

  }

  private FakeCrashLibrary() {
    throw new AssertionError("No instances.");

  }
}
