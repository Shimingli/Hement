package com.shiming.network.down.down;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/15 10:14
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public DownloadInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadResponseBody(originalResponse.body(), listener))
                .build();
    }
}
