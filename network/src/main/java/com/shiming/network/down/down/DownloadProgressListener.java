package com.shiming.network.down.down;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/19 15:18
 */

public interface DownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);
}
