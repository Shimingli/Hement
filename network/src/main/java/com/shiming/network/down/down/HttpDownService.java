package com.shiming.network.down.down;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/15 10:14
 */
public interface HttpDownService {

    /*断点续传下载接口*/
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);

}
