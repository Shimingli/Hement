package com.shiming.hement.data.remote;

import com.shiming.hement.HementApplication;
import com.shiming.hement.data.model.TodayBean;
import com.shiming.network.retrofit.SMResponse;
import com.shiming.network.retrofit.SMRetrofit;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/29 10:45
 */

public interface IRemoteServer {
    /**
     * http://v.juhe.cn/todayOnhistory/queryEvent.php?key=b15674dbd34ec00ded57b369dfdabd90&date=1/1
     * @param key 申请的key
     * @param date 日期
     * @return 返回历史上的今天
     */
    @GET("queryEvent.php")
    Observable<SMResponse<ArrayList<TodayBean>>> getToday(@Query("key") String key, @Query("date") String date);



    class Creator {
        public static IRemoteServer newHementService() {
          return  SMRetrofit.getService(HementApplication.getContext(), IRemoteServer.class);
        }
    }
}
